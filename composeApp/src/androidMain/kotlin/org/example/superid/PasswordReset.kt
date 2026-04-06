package org.example.superid

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import org.example.superid.ui.theme.SuperIdTheme
import org.example.superid.data.repository.AndroidAuthRepository
import org.example.superid.presentation.resetpassword.PasswordResetScreen
import org.example.superid.presentation.resetpassword.ResetPasswordViewModel
import org.example.superid.ui.theme.ui.common.StatusAndNavigationBarColors

class PasswordReset : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val authRepository = AndroidAuthRepository(this)
        val viewModel = ResetPasswordViewModel(authRepository)
        setContent {
            SuperIdTheme {
                StatusAndNavigationBarColors()
                PasswordResetScreen(
                    viewModel = viewModel,
                    onNavigateToLogin = {
                        startActivity(Intent(this, LogInActivity::class.java))
                        finish()
                    }
                )
            }
        }
    }
}