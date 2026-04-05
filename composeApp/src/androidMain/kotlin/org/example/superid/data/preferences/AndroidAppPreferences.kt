package org.example.superid.data.preferences

import android.content.Context
import android.content.SharedPreferences
import org.example.superid.domain.preferences.AppPreferences

class AndroidAppPreferences(context: Context) : AppPreferences {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("onboarding_prefs", Context.MODE_PRIVATE)

    override fun hasSeenOnboarding(): Boolean {
        return prefs.getBoolean("has_seen_onboarding", false)
    }

    override fun setHasSeenOnboarding(hasSeen: Boolean) {
        prefs.edit().putBoolean("has_seen_onboarding", hasSeen).apply()
    }
}