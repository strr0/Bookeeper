package com.example.bookkeeper.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import com.example.bookkeeper.data.model.UmsUser

@Dao
interface UmsUserDao {
    @Query("select * from ums_user where username = :username limit 1")
    suspend fun getUserByUsername(username: String): UmsUser?

    @Update
    suspend fun updateUser(user: UmsUser)
}