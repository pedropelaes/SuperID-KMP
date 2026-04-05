package com.example.superid.security

expect class CryptoManager() {

    // Pega a Senha Mestra que o usuário digitou e transforma numa Chave AES de 256 bits
    fun generateKeyFromPassword(password: String, salt: String): String

    // Criptografa o texto e devolve o Texto Cifrado e o IV (Vetor de Inicialização)
    fun encrypt(plainText: String, secretKeyBase64: String): Pair<String, String>

    // Descriptografa o texto usando o IV e a Chave
    fun decrypt(encryptedText: String, ivBase64: String, secretKeyBase64: String): String
}