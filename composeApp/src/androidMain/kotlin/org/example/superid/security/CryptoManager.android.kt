package com.example.superid.security

import android.util.Base64
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec

actual class CryptoManager actual constructor() {

    actual fun generateKeyFromPassword(password: String, salt: String): String {
        // PBKDF2: Gira a senha 10.000 vezes para evitar ataques de força bruta
        val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
        val spec = PBEKeySpec(password.toCharArray(), salt.toByteArray(), 10000, 256)
        val secretKey = factory.generateSecret(spec)

        return Base64.encodeToString(secretKey.encoded, Base64.NO_WRAP)
    }

    actual fun encrypt(plainText: String, secretKeyBase64: String): Pair<String, String> {
        val decodedKey = Base64.decode(secretKeyBase64, Base64.NO_WRAP)
        val secretKey = SecretKeySpec(decodedKey, 0, decodedKey.size, "AES")

        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)

        val iv = Base64.encodeToString(cipher.iv, Base64.NO_WRAP)
        val encryptedData = Base64.encodeToString(cipher.doFinal(plainText.toByteArray(Charsets.UTF_8)), Base64.NO_WRAP)

        return Pair(encryptedData, iv)
    }

    actual fun decrypt(encryptedText: String, ivBase64: String, secretKeyBase64: String): String {
        val decodedKey = Base64.decode(secretKeyBase64, Base64.NO_WRAP)
        val secretKey = SecretKeySpec(decodedKey, 0, decodedKey.size, "AES")
        val iv = Base64.decode(ivBase64, Base64.NO_WRAP)

        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        cipher.init(Cipher.DECRYPT_MODE, secretKey, IvParameterSpec(iv))

        val decryptedData = cipher.doFinal(Base64.decode(encryptedText, Base64.NO_WRAP))
        return String(decryptedData, Charsets.UTF_8)
    }

    actual fun generateAccessToken(): String {
        val randomBytes = ByteArray(192)   // Gera um token aleatório de 256 caracteres (usado como accessToken da senha).
        SecureRandom().nextBytes(randomBytes)
        return Base64.encodeToString(randomBytes, Base64.NO_WRAP)
    }
}