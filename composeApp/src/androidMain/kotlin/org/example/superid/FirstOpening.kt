package org.example.superid

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import org.example.superid.data.preferences.AndroidAppPreferences
import org.example.superid.presentation.onboarding.FirstOpeningScreen
import org.example.superid.presentation.onboarding.OnboardingViewModel
import org.example.superid.ui.theme.SuperIdTheme
import org.example.superid.ui.theme.ui.common.StatusAndNavigationBarColors


class FirstOpeningActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val preferences = AndroidAppPreferences(this)
        val viewModel = OnboardingViewModel(preferences)

        setContent {
            SuperIdTheme {
                StatusAndNavigationBarColors() // função que define a cor das barras do android
                FirstOpeningScreen(
                    viewModel = viewModel,
                    navToLogIn = {
                        val intent = Intent(this, LogInActivity::class.java)
                        startActivity(intent)
                        finish()
                    },
                    navToSignUp = {
                        val intent = Intent(this, SignUpActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                )
            }
        }
    }
}

