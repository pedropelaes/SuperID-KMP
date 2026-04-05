package org.example.superid.security

import android.util.Log
import com.example.superid.security.CryptoManager
import com.google.firebase.firestore.FirebaseFirestore
import utils.ChaveAesUtils

object SecurityFlowManager {
    private val cryptoManager = CryptoManager()

    // gera chave, encripta com a senha mestre e guarda no banco
    fun setupNewUserSecurity(
        uid: String,
        masterPassword: String,
        db: FirebaseFirestore,
        vault: AndroidBiometricVault,
        onComplete: (Boolean) -> Unit
    ){
        try {
            vault.saveMasterPassword(masterPassword)

            val keyEncryptionKey = cryptoManager.generateKeyFromPassword(masterPassword, uid)

            val rawAesKey = ChaveAesUtils.gerarChaveAesBase64()

            val (encryptedAesKey, iv) = cryptoManager.encrypt(rawAesKey, keyEncryptionKey)

            val securityData = mapOf(
                "encryptedAESKey" to encryptedAesKey,
                "keyIV" to iv
            )

            db.collection("users").document(uid)
                .update(securityData)
                .addOnSuccessListener { onComplete(true) }
                .addOnFailureListener { onComplete(false) }
        }catch (e: Exception){
            Log.e("SECURITY", "Erro ao configurar segurança", e)
            onComplete(false)
        }
    }

    // decifra chave AES do firestore
    fun retrieveDecryptedAesKey(
        uid: String,
        masterPassword: String,
        db: FirebaseFirestore,
        onSuccess: (String) -> Unit,
        onFailure: (Exception) -> Unit
    ){
        db.collection("users").document(uid).get()
            .addOnSuccessListener { doc ->
                val encryptedKey = doc.getString("encryptedAESKey")
                val iv = doc.getString("keyIV")

                if(encryptedKey != null && iv != null){
                    try{
                        val keyEncryptionKey = cryptoManager.generateKeyFromPassword(masterPassword, salt = uid)
                        val decryptedAesKey = cryptoManager.decrypt(encryptedKey, iv, keyEncryptionKey)
                        onSuccess(decryptedAesKey)
                    }catch (e: Exception){
                        onFailure(Exception("Senha mestra incorreta ou erro de decifragem"))
                    }
                }else {
                    onFailure(Exception("Chaves de segurança não encontradas no banco"))
                }
            }
            .addOnFailureListener { onFailure(it) }
    }
}