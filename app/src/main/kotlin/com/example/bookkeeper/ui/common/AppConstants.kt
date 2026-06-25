package com.example.bookkeeper.ui.common

interface AppConstants {
    companion object {
        const val PREFS_NAME: String = "bookkeeper_prefs"
        const val KEY_IS_LOGGED_IN: String = "is_logged_in"

        val ZODIAC: List<String> = listOf("rat", "ox", "tiger", "rabbit", "dragon", "snake", "horse", "goat", "monkey", "rooster", "dog", "pig")
    }
}
