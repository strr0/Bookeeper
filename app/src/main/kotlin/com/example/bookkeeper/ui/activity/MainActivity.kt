package com.example.bookkeeper.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.bookkeeper.R
import com.example.bookkeeper.databinding.ActivityMainBinding
import com.example.bookkeeper.ui.common.AppConstants

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_dashboard,
                R.id.navigation_diary,
                R.id.navigation_diary_edit,
                R.id.navigation_contact,
                R.id.navigation_profile,
                R.id.navigation_contact_manage,
                R.id.navigation_contact_manage_edit,
                R.id.navigation_rule_manage
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            findNavController(R.id.nav_host_fragment_activity_main).popBackStack()
        }
        return super.onOptionsItemSelected(item)
    }

    // 显示导航栏
    fun showNavView() {
        val navView: BottomNavigationView = binding.navView
        navView.visibility = View.VISIBLE
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

    // 隐藏导航栏
    fun hideNavView() {
        val navView: BottomNavigationView = binding.navView
        navView.visibility = View.GONE
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    // 退出登录
    fun logout() {
        val prefs = getSharedPreferences(AppConstants.PREFS_NAME, Context.MODE_PRIVATE)
        if (prefs.getBoolean(AppConstants.KEY_IS_LOGGED_IN, false)) {
            prefs.edit().putBoolean(AppConstants.KEY_IS_LOGGED_IN, false).apply()
        }
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}