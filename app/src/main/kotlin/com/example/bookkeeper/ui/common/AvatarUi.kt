package com.example.bookkeeper.ui.common

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.bookkeeper.R

object AvatarUi {

    private val avatarColorRes = intArrayOf(
        R.color.avatar_color_1,
        R.color.avatar_color_2,
        R.color.avatar_color_3,
        R.color.avatar_color_4,
        R.color.avatar_color_5,
        R.color.avatar_color_6,
    )

    fun avatarInitial(name: String?): String {
        val trimmed = name?.trim().orEmpty()
        return if (trimmed.isEmpty()) "?" else trimmed.takeLast(1)
    }

    fun bindAvatar(context: Context, avatarView: TextView, name: String?) {
        avatarView.text = avatarInitial(name)
        val colorRes = avatarColorRes[(name?.hashCode()?.let { kotlin.math.abs(it) } ?: 0) % avatarColorRes.size]
        val drawable = GradientDrawable().apply {
            shape = GradientDrawable.OVAL
            setColor(ContextCompat.getColor(context, colorRes))
        }
        avatarView.background = drawable
    }
}
