package org.example.superid.domain.repository

interface AuthRepository {
    suspend fun login(email: String, password: String) : Result<Unit>

    suspend fun signUp(name: String, email: String, masterPass: String, criptoPass: String) : Result<Unit>

    suspend fun checkIfEmailExists(email: String): Boolean
}