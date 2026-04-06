package org.example.superid.presentation.login

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.example.superid.domain.repository.AuthRepository

class LoginViewModel(private val authRepository: AuthRepository) {
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private val _loginSuccess = MutableStateFlow(false)
    val loginSuccess: StateFlow<Boolean> = _loginSuccess.asStateFlow()

    fun login(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) return

        _isLoading.value = true
        _errorMessage.value = null

        CoroutineScope(Dispatchers.Main).launch {
            val result = authRepository.login(email, password)
            _isLoading.value = false

            if (result.isSuccess) {
                _loginSuccess.value = true
            } else {
                _errorMessage.value = "E-mail ou senha incorretos." // Mensagem universal de erro
            }
        }
    }

    fun clearError(){
        _errorMessage.value = null
    }
}