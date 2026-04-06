package org.example.superid.presentation.login

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.superid.core.resources.AppStrings
import org.example.superid.ui.common.LoginAndSignUpDesign
import org.example.superid.ui.common.SuperIdTitlePainterVerified
import org.example.superid.ui.common.TextFieldDesignForLoginAndSignUp

@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    onNavigateToMain: () -> Unit,
    onNavigateToSignUp: () -> Unit,
    onNavigateToResetPassword: () -> Unit
){
    LoginAndSignUpDesign(content = {
        val isLoading by viewModel.isLoading.collectAsState()
        val errorMessage by viewModel.errorMessage.collectAsState()
        val loginSuccess by viewModel.loginSuccess.collectAsState()

        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }

        LaunchedEffect(loginSuccess) {
            if (loginSuccess) {
                onNavigateToMain()
            }
        }

        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally){
            Spacer(modifier = Modifier.height(64.dp))
            SuperIdTitlePainterVerified()

            Spacer(modifier = Modifier.height(24.dp))

            Text("Entrar:",fontFamily = FontFamily.SansSerif ,fontSize = 30.sp, color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold, modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(24.dp))

            TextFieldDesignForLoginAndSignUp(value = email, onValueChange = { email = it },
                label = AppStrings.TYPE_YOUR_EMAIL
            )

            Spacer(modifier = Modifier.height(12.dp))

            TextFieldDesignForLoginAndSignUp(value = password, onValueChange = { password = it },
                label = AppStrings.TYPE_YOUR_PASSWORD, isPassword = true
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    viewModel.login(email, password)
                },
                enabled = email.isNotEmpty() && password.isNotEmpty(),
                border = BorderStroke(2.dp, MaterialTheme.colorScheme.secondary),
                colors = ButtonDefaults.buttonColors(
                    disabledContainerColor = MaterialTheme.colorScheme.primary.copy(0.5f),
                    containerColor = MaterialTheme.colorScheme.primary,
                    disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant
                ),
                modifier = Modifier
                    .fillMaxWidth(0.85f) // Igual às caixas de texto
                    .height(50.dp)
            ){
                if (isLoading) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimary, modifier = Modifier.size(24.dp))
                } else {
                    Text("Entrar")
                }
            }
            Spacer(modifier = Modifier.height(2.dp))
            if (errorMessage != null) {
                Text(
                    text = errorMessage!!,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 14.sp
                )
            } else {
                Spacer(modifier = Modifier.height(20.dp)) // Mantém o espaço para a tela não pular
            }

            TextButton(
                onClick = onNavigateToResetPassword,
                modifier = Modifier
                    .height(45.dp)
                    .defaultMinSize(minWidth = 190.dp)
                    .wrapContentSize()
            ) {
                Text("Esqueceu sua senha?", textDecoration = TextDecoration.Underline, color = MaterialTheme.colorScheme.onBackground)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Spacer(modifier = Modifier.height(24.dp))

            Text("Ainda não possui conta?", color = MaterialTheme.colorScheme.onBackground)
            TextButton(
                onClick = onNavigateToSignUp,
                modifier = Modifier
                    .height(45.dp)
                    .defaultMinSize(minWidth = 160.dp)
            ) {
                Text("Fazer Cadastro", textDecoration = TextDecoration.Underline, color = MaterialTheme.colorScheme.onBackground)
            }
        }
    })
}