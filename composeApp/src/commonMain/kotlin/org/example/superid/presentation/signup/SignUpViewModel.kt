package org.example.superid.presentation.signup

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.example.superid.domain.repository.AuthRepository
import org.example.superid.isEmailValid

class SignUpViewModel(private val authRepository: AuthRepository) {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private val _signUpSuccess = MutableStateFlow(false)
    val signUpSuccess: StateFlow<Boolean> = _signUpSuccess.asStateFlow()

    fun signUp(name: String, email: String, masterPassword: String, criptoPassword: String) {
        if (!isEmailValid(email)) {
            _errorMessage.value = "Por favor, insira um e-mail válido."
            return
        }
        if (masterPassword.length < 6) {
            _errorMessage.value = "A senha deve ter pelo menos 6 caracteres."
            return
        }
        if (criptoPassword.isEmpty()) {
            _errorMessage.value = "Digite a senha de criptografia"
            return
        }

        _isLoading.value = true
        _errorMessage.value = null

        CoroutineScope(Dispatchers.Main).launch {
            val emailExists = authRepository.checkIfEmailExists(email)

            if (emailExists) {
                _isLoading.value = false
                _errorMessage.value = "Já existe uma conta com este e-mail."
                return@launch
            }

            val result = authRepository.signUp(name, email, masterPassword, criptoPassword)

            _isLoading.value = false

            if (result.isSuccess) {
                _signUpSuccess.value = true
            } else {
                _errorMessage.value = result.exceptionOrNull()?.message ?: "Erro ao criar conta."
            }
        }
    }

    fun clearError() {
        _errorMessage.value = null
    }
}