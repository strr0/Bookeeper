package com.example.bookkeeper.ui.common

interface AppConstants {
    companion object {
        const val PREFS_NAME: String = "bookkeeper_prefs"
        const val KEY_IS_LOGGED_IN: String = "is_logged_in"
        const val KEY_LAST_USERNAME: String = "last_username"
        const val KEY_CURRENT_USER_ID: String = "current_user_id"
        const val KEY_REMEMBER_ME: String = "remember_me"
    }

    enum class Area(val id: Int, val code: String, val text: String) {
        HK(1, "hk", "香港"),
        MO(2, "mo", "澳门")
    }

    enum class Zodiac(val id: Int, val code: String, val text: String) {
        RAT(1, "rat", "鼠"),
        OX(2, "ox", "牛"),
        TIGER(3, "tiger", "虎"),
        RABBIT(3, "rabbit", "兔"),
        DRAGON(3, "dragon", "龙"),
        SNAKE(3, "snake", "蛇"),
        HORSE(3, "horse", "马"),
        GOAT(3, "goat", "羊"),
        MONKEY(3, "monkey", "猴"),
        ROOSTER(3, "rooster", "鸡"),
        DOG(3, "dog", "狗"),
        PIG(3, "pig", "猪")
    }
}
