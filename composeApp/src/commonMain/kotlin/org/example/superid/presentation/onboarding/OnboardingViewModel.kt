package org.example.superid.presentation.onboarding

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.example.superid.domain.preferences.AppPreferences

class OnboardingViewModel(
    private val preferences: AppPreferences
) {
    private val _shouldShowOnboarding = MutableStateFlow(true)
    val shouldShowOnboarding: StateFlow<Boolean> = _shouldShowOnboarding.asStateFlow()

    init {
        checkOnboardingStatus()
    }

    private fun checkOnboardingStatus() {
        val hasSeen = preferences.hasSeenOnboarding()
        _shouldShowOnboarding.value = !hasSeen
    }

    fun completeOnboarding() {
        preferences.setHasSeenOnboarding(true)
        _shouldShowOnboarding.value = false
    }
}