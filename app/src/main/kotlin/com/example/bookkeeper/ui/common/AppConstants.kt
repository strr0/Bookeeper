package com.example.bookkeeper.ui.common

interface AppConstants {
    companion object {
        const val PREFS_NAME: String = "bookkeeper_prefs"
        const val KEY_IS_LOGGED_IN: String = "is_logged_in"

        val ZODIAC: List<String> = listOf("rat", "ox", "tiger", "rabbit", "dragon", "snake", "horse", "goat", "monkey", "rooster", "dog", "pig")

    }

    enum class Area(val id: Int, val code: String, val text: String) {
        HK(1, "hk", "香港"),
        MO(2, "mo", "澳门")
    }
}
