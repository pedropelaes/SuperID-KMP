package org.example.superid.presentation.resetpassword

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.example.superid.domain.repository.AuthRepository
import org.example.superid.isEmailValid // Sua função KMP de Regex

class ResetPasswordViewModel(private val authRepository: AuthRepository) {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _message = MutableStateFlow<String?>(null) // Pode ser erro ou sucesso
    val message: StateFlow<String?> = _message.asStateFlow()

    private val _isSuccess = MutableStateFlow(false)
    val isSuccess: StateFlow<Boolean> = _isSuccess.asStateFlow()

    fun resetPassword(email: String) {
        val cleanEmail = email.trim().lowercase()

        if (!isEmailValid(cleanEmail)) {
            _message.value = "Por favor, digite um e-mail válido."
            _isSuccess.value = false
            return
        }

        _isLoading.value = true
        _message.value = null

        CoroutineScope(Dispatchers.Main).launch {
            val result = authRepository.sendPasswordReset(cleanEmail)

            _isLoading.value = false

            if (result.isSuccess) {
                _isSuccess.value = true
                _message.value = "Um e-mail de recuperação foi enviado."
            } else {
                _isSuccess.value = false
                _message.value = result.exceptionOrNull()?.message ?: "Erro desconhecido."
            }
        }
    }

    fun clearMessage() {
        _message.value = null
    }
}