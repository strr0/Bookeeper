package com.example.bookkeeper.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.bookkeeper.R
import com.example.bookkeeper.databinding.ActivityLoginBinding
import com.example.bookkeeper.ui.common.AppConstants

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()

        val prefs = getSharedPreferences(AppConstants.PREFS_NAME, Context.MODE_PRIVATE)
        if (prefs.getBoolean(AppConstants.KEY_IS_LOGGED_IN, false)) {
            navigateToMain()
            return
        }

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginButton.setOnClickListener {
            val username = binding.usernameInput.text?.toString().orEmpty().trim()
            val password = binding.passwordInput.text?.toString().orEmpty()

            binding.usernameLayout.error = null
            binding.passwordLayout.error = null

            when {
                username.isEmpty() -> binding.usernameLayout.error = getString(R.string.login_username_empty_tip)
                password.isEmpty() -> binding.passwordLayout.error = getString(R.string.login_password_empty_tip)
                else -> {
                    if (binding.rememberCheck.isChecked) {
                        prefs.edit().putBoolean(AppConstants.KEY_IS_LOGGED_IN, true).apply()
                    }
                    navigateToMain()
                }
            }
        }
    }

    private fun navigateToMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
