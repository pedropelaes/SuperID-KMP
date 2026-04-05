package org.example.superid.security

interface BiometricVault {
    // verifica se há senha mestra salva no dispositivo
    fun hasPasswordSaved() : Boolean

    // guarda senha mestra no dispositivo
    fun saveMasterPassword(password: String)

    // pede a biometria e devolve a senha
    fun retrieveMasterPassword(
        title: String,
        subtitle: String,
        onSuccess: (String) -> Unit,
        onError: (String) -> Unit
    )
}