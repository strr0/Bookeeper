package com.example.bookkeeper.data.repository

import android.app.Application
import com.example.bookkeeper.data.dao.AppDatabase
import com.example.bookkeeper.data.dao.UmsUserDao
import com.example.bookkeeper.data.model.UmsUser
import com.example.bookkeeper.util.PasswordUtil

class AuthRepository(application: Application) {
    private val _umsUserDao: UmsUserDao? = AppDatabase.getInstance(application).umsUserDao()
    private val umsUserDao: UmsUserDao = _umsUserDao!!

    suspend fun updateUserPassword(user: UmsUser) {
        val salt = PasswordUtil.generateSalt()
        user.password = PasswordUtil.hashPassword(user.password!!, salt)
        user.salt = PasswordUtil.toHexString(salt)
        umsUserDao.updateUser(user)
    }

    suspend fun login(username: String, password: String): Boolean {
        val user = umsUserDao.getUserByUsername(username) ?: return false
        val salt = PasswordUtil.hexStringToByteArray(user.salt!!)
        return PasswordUtil.verifyPassword(password, user.password!!, salt)
    }
}