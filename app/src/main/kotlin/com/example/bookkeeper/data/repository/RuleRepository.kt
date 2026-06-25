package com.example.bookkeeper.data.repository

import android.app.Application
import com.example.bookkeeper.data.dao.AppDatabase
import com.example.bookkeeper.data.dao.RmsRuleDao
import com.example.bookkeeper.data.model.RmsRule
import kotlinx.coroutines.flow.Flow

class RuleRepository(application: Application) {
    private val _rmsRuleDao: RmsRuleDao? = AppDatabase.getInstance(application).rmsRuleDao()
    private val rmsRuleDao: RmsRuleDao = _rmsRuleDao!!

    fun listAllRules(): Flow<List<RmsRule>> = rmsRuleDao.listAllRules()
}