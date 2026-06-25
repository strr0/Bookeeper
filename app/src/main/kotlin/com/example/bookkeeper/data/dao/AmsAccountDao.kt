package com.example.bookkeeper.data.dao

import androidx.room.*
import com.example.bookkeeper.data.model.AmsAccount
import kotlinx.coroutines.flow.Flow

@Dao
interface AmsAccountDao {
    @Query("select * from ams_account")
    fun listAllAccounts(): Flow<List<AmsAccount>>

    @Insert
    suspend fun saveAccount(record: AmsAccount)

    @Update
    suspend fun updateAccount(record: AmsAccount)

    @Delete
    suspend fun removeAccount(record: AmsAccount)
}