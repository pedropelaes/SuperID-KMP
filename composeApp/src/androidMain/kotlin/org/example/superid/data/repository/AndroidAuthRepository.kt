package org.example.superid.data.repository

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import org.example.superid.domain.repository.AuthRepository

class AndroidAuthRepository : AuthRepository {
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
}