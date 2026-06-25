package com.example.bookkeeper.data.repository

import android.app.Application
import com.example.bookkeeper.data.dao.AmsAccountDao
import com.example.bookkeeper.data.dao.AppDatabase
import com.example.bookkeeper.data.dao.BmsBillDao
import com.example.bookkeeper.data.model.AmsAccount
import com.example.bookkeeper.data.vo.BmsBillVo
import com.example.bookkeeper.util.DateUtil
import kotlinx.coroutines.flow.Flow

class DiaryRepository(application: Application) {
    private val _amsAccountDao: AmsAccountDao? = AppDatabase.getInstance(application).amsAccountDao()
    private val _bmsBillDao: BmsBillDao? = AppDatabase.getInstance(application).bmsBillDao()
    private val amsAccountDao: AmsAccountDao = _amsAccountDao!!
    private val bmsBillDao: BmsBillDao = _bmsBillDao!!

    fun listBills(date: Long): Flow<List<BmsBillVo>> = bmsBillDao.listBills(date, DateUtil.getEndDayTimestamp(date))

    fun listAllAccounts(): Flow<List<AmsAccount>> = amsAccountDao.listAllAccounts()
}