package org.example.superid

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import org.example.superid.ui.theme.SuperIdTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import android.provider.Settings
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.fragment.app.FragmentActivity
import org.example.superid.data.repository.AndroidAuthRepository
import org.example.superid.presentation.signup.SignUpScreen
import org.example.superid.presentation.signup.SignUpViewModel
import org.example.superid.security.AndroidBiometricVault
import org.example.superid.security.SecurityFlowManager
import org.example.superid.ui.theme.ui.common.StatusAndNavigationBarColors

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val authRepository = AndroidAuthRepository(this)
        val viewModel = SignUpViewModel(authRepository)
        setContent {
            SuperIdTheme {
                StatusAndNavigationBarColors(                                                                   // define as cores das barras do android
                    MaterialTheme.colorScheme.background,
                    MaterialTheme.colorScheme.background
                )
                SignUpScreen(
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
