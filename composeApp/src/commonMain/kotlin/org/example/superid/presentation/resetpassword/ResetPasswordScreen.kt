package org.example.superid.presentation.resetpassword

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
fun PasswordResetScreen(
    viewModel: ResetPasswordViewModel,
    onNavigateToLogin: () -> Unit
) {
    LoginAndSignUpDesign(content = {
        val isLoading by viewModel.isLoading.collectAsState()
        val message by viewModel.message.collectAsState()
        val isSuccess by viewModel.isSuccess.collectAsState()

        var email by remember { mutableStateOf("") }

        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SuperIdTitlePainterVerified()

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                "Recuperar Senha",
                fontFamily = FontFamily.SansSerif,
                fontSize = 30.sp,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Por favor, insira o e-mail associado à sua conta. Enviaremos um link para recuperação de senha.",
                fontFamily = FontFamily.SansSerif,
                fontSize = 15.sp,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Atenção: Apenas contas com e-mail verificado poderão alterar a senha.",
                fontFamily = FontFamily.SansSerif,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            TextFieldDesignForLoginAndSignUp(
                value = email,
                onValueChange = { email = it },
                label = AppStrings.TYPE_YOUR_EMAIL
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    viewModel.resetPassword(email.trim())
                },
                enabled = email.isNotEmpty(),
                border = BorderStroke(2.dp, MaterialTheme.colorScheme.surface),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    disabledContainerColor = MaterialTheme.colorScheme.primary.copy(0.5f),
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant
                ),
                modifier = Modifier
                    .height(56.dp)
                    .fillMaxWidth(0.5f)
            ) {
                Text("Enviar E-mail")
            }
            if (message != null) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = message!!,
                    color = if (isSuccess) Color(0xFF4CAF50) else MaterialTheme.colorScheme.error,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            } else {
                Spacer(modifier = Modifier.height(20.dp))
            }
        }

        Box(modifier = Modifier.fillMaxSize()) {
            TextButton(
                onClick = onNavigateToLogin,
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.BottomStart)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Voltar",
                    tint = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    "Voltar",
                    fontSize = 15.sp,
                    color = MaterialTheme.colorScheme.onBackground,
                    textDecoration = TextDecoration.Underline
                )
            }
        }
    })
}