package com.example.bookkeeper.data.repository

import android.app.Application
import com.example.bookkeeper.data.dao.AmsAccountDao
import com.example.bookkeeper.data.dao.AppDatabase
import com.example.bookkeeper.data.dao.RmsRuleDao
import com.example.bookkeeper.data.model.AmsAccount
import com.example.bookkeeper.data.vo.RmsRuleVo
import kotlinx.coroutines.flow.Flow

class ContactRepository(application: Application) {
    private val _amsAccountDao: AmsAccountDao? = AppDatabase.getInstance(application).amsAccountDao()
    private val _rmsRuleDao: RmsRuleDao? = AppDatabase.getInstance(application).rmsRuleDao()
    private val amsAccountDao: AmsAccountDao = _amsAccountDao!!
    private val rmsRuleDao: RmsRuleDao = _rmsRuleDao!!

    fun listAllAccounts() = amsAccountDao.listAllAccounts()

    fun listAccRules(accId: Int): Flow<List<RmsRuleVo>> = rmsRuleDao.listAccRules(accId)

    suspend fun saveAccount(record: AmsAccount) = amsAccountDao.saveAccount(record)

    suspend fun updateAccount(record: AmsAccount) = amsAccountDao.updateAccount(record)

    suspend fun removeAccount(record: AmsAccount) = amsAccountDao.removeAccount(record)
}