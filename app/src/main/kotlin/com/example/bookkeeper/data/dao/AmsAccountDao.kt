package com.example.bookkeeper.data.dao

import androidx.room.*
import com.example.bookkeeper.data.model.AmsAccount
import com.example.bookkeeper.data.model.AmsAccountDetail
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

    @Query("select * from ams_account_detail where acc_id=:accId")
    fun listAccountDetails(accId: Int): Flow<List<AmsAccountDetail>>
}