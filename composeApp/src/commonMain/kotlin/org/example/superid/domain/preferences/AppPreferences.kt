package org.example.superid.domain.preferences

interface AppPreferences {
    fun hasSeenOnboarding(): Boolean
    fun setHasSeenOnboarding(hasSeen: Boolean)
}