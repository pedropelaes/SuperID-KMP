package org.example.superid.presentation.signup

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.superid.core.resources.AppStrings
import org.example.superid.ui.common.LoginAndSignUpDesign
import org.example.superid.ui.common.SuperIdTitlePainterVerified
import org.example.superid.ui.common.TextFieldDesignForLoginAndSignUp

@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel,
    onNavigateToLogin: () -> Unit
) {
    LoginAndSignUpDesign(content = {
        val isLoading by viewModel.isLoading.collectAsState()
        val errorMessage by viewModel.errorMessage.collectAsState()
        val signUpSuccess by viewModel.signUpSuccess.collectAsState()

        var name by remember { mutableStateOf("") }
        var email by remember { mutableStateOf("") }
        var masterPassword by remember { mutableStateOf("") }
        var confirmPassword by remember { mutableStateOf("") }
        var criptoPassword by remember { mutableStateOf("") }

        LaunchedEffect(signUpSuccess) {
            if (signUpSuccess) {
                onNavigateToLogin()
            }
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(36.dp))
            SuperIdTitlePainterVerified()
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                "Crie sua conta:",
                fontFamily = FontFamily.SansSerif,
                fontSize = 30.sp,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(24.dp))

            TextFieldDesignForLoginAndSignUp(
                value = name,
                onValueChange = { name = it },
                label = AppStrings.TYPE_YOUR_NAME
            )

            Spacer(modifier = Modifier.height(12.dp))

            TextFieldDesignForLoginAndSignUp(
                value = email,
                onValueChange = { email = it },
                label = AppStrings.TYPE_YOUR_EMAIL
            )

            Spacer(modifier = Modifier.height(12.dp))

            TextFieldDesignForLoginAndSignUp(
                value = masterPassword,
                onValueChange = { masterPassword = it },
                label = AppStrings.TYPE_YOUR_PASSWORD,
                isPassword = true
            )

            Spacer(modifier = Modifier.height(12.dp))

            TextFieldDesignForLoginAndSignUp(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = AppStrings.CONFIRM_YOUR_PASSWORD,
                isPassword = true
            )

            Spacer(modifier = Modifier.height(12.dp))

            TextFieldDesignForLoginAndSignUp(
                value = criptoPassword,
                onValueChange = { criptoPassword = it },
                label = "Digite sua senha de criptografia",
                isPassword = true
            )

            Spacer(modifier = Modifier.height(8.dp))

            if (masterPassword.isNotEmpty() && confirmPassword.isNotEmpty() && masterPassword != confirmPassword) {
                Text(
                    AppStrings.PASSWORDS_MUST_MATCH,
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center,
                    fontSize = 12.sp
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    viewModel.signUp(name, email, masterPassword, criptoPassword)
                },
                enabled = !isLoading && masterPassword == confirmPassword &&
                        name.isNotEmpty() && email.isNotEmpty() && masterPassword.isNotEmpty(),
                border = BorderStroke(2.dp, MaterialTheme.colorScheme.secondary),
                colors = ButtonDefaults.buttonColors(
                    disabledContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                    containerColor = MaterialTheme.colorScheme.primary,
                    disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant
                ),
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .height(50.dp)
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.height(24.dp).width(24.dp),
                        color = MaterialTheme.colorScheme.onPrimary,
                        strokeWidth = 2.dp
                    )
                } else {
                    Text("Fazer Cadastro")
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            if (errorMessage != null) {
                Text(
                    text = errorMessage!!,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text("Já possui conta?", color = MaterialTheme.colorScheme.onBackground)
            Spacer(modifier = Modifier.height(0.dp))
            TextButton(
                onClick = onNavigateToLogin,
                modifier = Modifier
                    .height(45.dp)
                    .width(160.dp)
            ) {
                Text("Login", textDecoration = TextDecoration.Underline, color = MaterialTheme.colorScheme.onBackground)
            }
        }
    })
}