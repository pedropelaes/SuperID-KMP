package org.example.superid

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.superid.ui.theme.SuperIdTheme
import org.example.superid.ui.theme.ui.common.LoginAndSignUpDesign
import org.example.superid.ui.theme.ui.common.SuperIdTitlePainterVerified
import org.example.superid.ui.theme.ui.common.TextFieldDesignForLoginAndSignUp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.example.superid.data.repository.AndroidAuthRepository
import org.example.superid.presentation.login.LoginScreen
import org.example.superid.presentation.login.LoginViewModel

import org.example.superid.ui.theme.ui.common.StatusAndNavigationBarColors

class LogInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val authRepository = AndroidAuthRepository(this)
        val viewModel = LoginViewModel(authRepository)
        setContent{
            SuperIdTheme {
                StatusAndNavigationBarColors(                                                                   // define as cores das barras do android
                    MaterialTheme.colorScheme.background,
                    MaterialTheme.colorScheme.background
                )
                LoginScreen(
                    viewModel = viewModel,
                    onNavigateToMain = {
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    },
                    onNavigateToSignUp = {
                        startActivity(Intent(this, SignUpActivity::class.java))
                    },
                    onNavigateToResetPassword = {
                        startActivity(Intent(this, PasswordReset::class.java))
                    }
                ) 
            }
        }
    }
}

