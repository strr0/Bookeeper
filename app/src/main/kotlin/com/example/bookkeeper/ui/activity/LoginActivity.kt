package com.example.bookkeeper.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.bookkeeper.R
import com.example.bookkeeper.databinding.ActivityLoginBinding
import com.example.bookkeeper.ui.common.AppConstants
import com.example.bookkeeper.ui.viewmodel.AuthViewModel
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private var _binding: ActivityLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()

        val prefs = getSharedPreferences(AppConstants.PREFS_NAME, Context.MODE_PRIVATE)
        if (prefs.getBoolean(AppConstants.KEY_IS_LOGGED_IN, false)) {
            navigateToMain()
            return
        }

        val authViewModel: AuthViewModel by viewModels { AuthViewModel.Factory }

        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val savedUsername = prefs.getString(AppConstants.KEY_LAST_USERNAME, "")
        if (!savedUsername.isNullOrEmpty()) {
            binding.usernameInput.setText(savedUsername)
            binding.passwordInput.requestFocus()
        }

        binding.passwordInput.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                binding.loginButton.performClick()
                true
            } else false
        }

        binding.loginButton.setOnClickListener {
            val username = binding.usernameInput.text?.toString().orEmpty().trim()
            val password = binding.passwordInput.text?.toString().orEmpty()

            binding.usernameLayout.error = null
            binding.passwordLayout.error = null

            when {
                username.isEmpty() -> {
                    binding.usernameLayout.error = getString(R.string.login_username_empty_tip)
                    binding.usernameInput.requestFocus()
                }
                password.isEmpty() -> {
                    binding.passwordLayout.error = getString(R.string.login_password_empty_tip)
                    binding.passwordInput.requestFocus()
                }
                else -> {
                    setLoading(true)

                    lifecycleScope.launch {
                        if (authViewModel.login(username, password)) {
                            prefs.edit()
                                .putString(AppConstants.KEY_LAST_USERNAME, username)
                                .putBoolean(AppConstants.KEY_IS_LOGGED_IN, true)
                                .apply()

                            if (binding.rememberCheck.isChecked) {
                                prefs.edit().putBoolean(AppConstants.KEY_REMEMBER_ME, true).apply()
                            }

                            navigateToMain()
                        } else {
                            binding.passwordLayout.error = getString(R.string.login_invalid_credentials_tip)
                            binding.passwordInput.text?.clear()
                            binding.passwordInput.requestFocus()
                        }

                        setLoading(false)
                    }
                }
            }
        }
    }

    private fun setLoading(loading: Boolean) {
        binding.loginButton.isEnabled = !loading
        binding.loginButton.text = if (loading) getString(R.string.login_logging) else getString(R.string.login_button)
        binding.usernameInput.isEnabled = !loading
        binding.passwordInput.isEnabled = !loading
    }

    private fun navigateToMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
