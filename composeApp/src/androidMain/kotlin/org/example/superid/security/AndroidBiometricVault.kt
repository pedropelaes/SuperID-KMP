package org.example.superid.security

import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import androidx.core.content.edit

class AndroidBiometricVault(
    private val activity: FragmentActivity // mostrar pop up
) : BiometricVault {
    // criando chave mestra nativa dentro do chip de segurança do dispositivo (Keystore)
    private val masterKey = MasterKey.Builder(activity)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    // criando ficheiro de preferencias encriptado
    private val sharedPreferences = EncryptedSharedPreferences.create(
        activity,
        "super_id_secure_prefs",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM,
    )

    override fun hasPasswordSaved(): Boolean {
        return sharedPreferences.contains("master_password")
    }

    override fun saveMasterPassword(password: String) {
        sharedPreferences.edit { putString("master_password", password) }
    }

    override fun retrieveMasterPassword(
        title: String,
        subtitle: String,
        onSuccess: (String) -> Unit,
        onError: (String) -> Unit
    ) {
        val executor = ContextCompat.getMainExecutor(activity)

        val biometricPrompt = BiometricPrompt(activity, executor,
            object : BiometricPrompt.AuthenticationCallback(){
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    onError(errString.toString())
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    val savedPassword = sharedPreferences.getString("master_password", null)
                    if(savedPassword != null) {
                        onSuccess(savedPassword)
                    }else{
                        onError("Senha mestra não encontrada")
                    }
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    onError("A biometria falhou. Tente novamente")
                }
            })

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle(title)
            .setSubtitle(subtitle)
            .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL)
            .build()

        biometricPrompt.authenticate(promptInfo)
    }

    fun getMasterPasswordSilently(): String? {
        return sharedPreferences.getString("master_password", null)
    }
}