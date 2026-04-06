package org.example.superid.data.repository

import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import org.example.superid.domain.repository.AuthRepository
import android.provider.Settings
import androidx.fragment.app.FragmentActivity
import kotlinx.coroutines.suspendCancellableCoroutine
import org.example.superid.security.AndroidBiometricVault
import org.example.superid.security.SecurityFlowManager
import kotlin.coroutines.resume
import android.content.Context
import com.google.firebase.functions.FirebaseFunctionsException
import com.google.firebase.functions.ktx.functions

class AndroidAuthRepository(private val context: Context) : AuthRepository {
    override suspend fun login(email: String, password: String): Result<Unit> {
        return try {
            val auth = Firebase.auth
            if (auth.currentUser != null) {
                auth.signOut()
            }

            auth.signInWithEmailAndPassword(email, password).await()
            Result.success(Unit)

        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun checkIfEmailExists(email: String): Boolean {
        return try {
            val result = Firebase.auth.fetchSignInMethodsForEmail(email).await()

            !result.signInMethods.isNullOrEmpty()
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun signUp(
        name: String,
        email: String,
        masterPass: String,
        criptoPass: String
    ): Result<Unit> {
        return try {
            val auth = Firebase.auth
            val db = Firebase.firestore

            // 1. Criar Usuário no Firebase Auth
            val authResult = auth.createUserWithEmailAndPassword(email, masterPass).await()
            val user = authResult.user ?: throw Exception("Erro: Usuário nulo após criação.")

            // 2. Salvar Conta no Firestore (Substitui o SaveNewAccount)
            val androidId = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID) ?: ""
            val taskDoc = hashMapOf(
                "name" to name,
                "email" to email,
                "androidId" to androidId
            )
            db.collection("users").document(user.uid).set(taskDoc).await()

            // 3. Salvar Categorias Padrão (Substitui o SaveUserDefaultCategories)
            val batch = db.batch()
            val categorias = listOf("Aplicativos", "E-mails", "Sites", "Teclados de acesso")
            val categoriasRef = db.collection("users").document(user.uid).collection("categorias")

            for (categoria in categorias) {
                val docRef = categoriasRef.document(categoria)
                batch.set(docRef, mapOf("nome" to categoria))

                val passwordPlaceHolder = docRef.collection("senhas").document("placeholder")
                batch.set(passwordPlaceHolder, mapOf("placeholder" to true))
            }
            batch.commit().await() // Aguarda o batch terminar

            // 4. Configurar Segurança / Cofre Biométrico
            val vault = AndroidBiometricVault(context as FragmentActivity)

            // Usamos suspendCoroutine para converter o Callback da sua função de segurança em uma Coroutine
            val securitySuccess = suspendCancellableCoroutine { continuation ->
                SecurityFlowManager.setupNewUserSecurity(
                    uid = user.uid,
                    masterPassword = criptoPass, // A senha de criptografia
                    db = db,
                    vault = vault
                ) { success ->
                    continuation.resume(success)
                }
            }

            if (!securitySuccess) {
                throw Exception("Falha ao configurar a segurança do dispositivo.")
            }

            // 5. Enviar E-mail de Verificação
            user.sendEmailVerification().await()

            // Tudo ocorreu com sucesso!
            Result.success(Unit)

        } catch (e: Exception) {
            // O próprio View Model pegará esse erro e jogará na tela em vez de um Toast
            Result.failure(e)
        }
    }

    override suspend fun sendPasswordReset(email: String): Result<Unit> {
        return try {
            val functions = Firebase.functions

            // 1. Chama a Cloud Function
            val httpsCallable = functions.getHttpsCallableFromUrl(
                java.net.URL("https://checkemailisverified-snp2owcvrq-rj.a.run.app")
            )

            val result = httpsCallable.call(mapOf("email" to email)).await()
            val data = result.data as? Map<*, *> ?: throw Exception("Erro: resposta inválida do servidor.")
            val isVerified = data["verified"] as? Boolean ?: false

            // 2. Se for verificado, envia o e-mail de reset
            if (isVerified) {
                Firebase.auth.sendPasswordResetEmail(email).await()
                Result.success(Unit)
            } else {
                throw Exception("Seu e-mail ainda não foi verificado.")
            }

        } catch (e: Exception) {
            // Tratamento específico de erros da Cloud Function (igual ao seu original)
            if (e is FirebaseFunctionsException) {
                val message = when (e.code) {
                    FirebaseFunctionsException.Code.NOT_FOUND -> "Nenhum usuário encontrado com esse e-mail."
                    FirebaseFunctionsException.Code.INVALID_ARGUMENT -> "E-mail inválido."
                    else -> "Erro na função: ${e.message}"
                }
                Result.failure(Exception(message))
            } else {
                Result.failure(e)
            }
        }
    }
}