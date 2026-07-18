package com.example.bookkeeper.data.repository

import android.app.Application
import com.example.bookkeeper.data.dao.AppDatabase
import com.example.bookkeeper.data.dao.DmsDigitDao
import com.example.bookkeeper.data.dao.LmsLotteryDao
import com.example.bookkeeper.data.model.LmsLottery
import com.example.bookkeeper.data.model.LmsLotteryDetail
import com.example.bookkeeper.data.vo.DmsDigitVo
import com.example.bookkeeper.data.vo.DmsZodiacVo
import com.example.bookkeeper.data.vo.LmsLotteryDetailVo
import com.example.bookkeeper.util.DateUtil
import kotlinx.coroutines.flow.Flow
import java.util.*

class DashboardRepository(application: Application) {
    private val _lmsLotteryDao: LmsLotteryDao? = AppDatabase.getInstance(application).lmsLotteryDao()
    private val _dmsDigitDao: DmsDigitDao? = AppDatabase.getInstance(application).dmsDigitDao()
    private val lmsLotteryDao: LmsLotteryDao = _lmsLotteryDao!!
    private val dmsDigitDao: DmsDigitDao = _dmsDigitDao!!

    fun listLotteryDetails(date: Long, area: String): Flow<List<LmsLotteryDetailVo>> = lmsLotteryDao.listLotteryDetails(date, DateUtil.getEndDayTimestamp(date), area)

    fun listDigits(date: Long, area: String): Flow<List<DmsDigitVo>> = dmsDigitDao.listDigits(date, DateUtil.getEndDayTimestamp(date), area)

    fun listZodiacs(date: Long, area: String): Flow<List<DmsZodiacVo>> = dmsDigitDao.listZodiacs(date, DateUtil.getEndDayTimestamp(date), area)

    suspend fun listAllDigits(): List<DmsDigitVo> = dmsDigitDao.listAllDigits()

    suspend fun listAllZodiacs(): List<DmsZodiacVo> = dmsDigitDao.listAllZodiacs()

    suspend fun saveLotteryDetail(date: Long, area: String, list: List<LmsLotteryDetail>) {
        val selectDate = Date(date)
        var lottery = lmsLotteryDao.getLottery(date, DateUtil.getEndDayTimestamp(date), area)
        if (lottery == null) {
            lottery = LmsLottery()
            lottery.name = DateUtil.formatDate(selectDate)
            lottery.area = area
            lottery.updateTime = selectDate
            lottery.status = "1"
            lottery.id = lmsLotteryDao.saveLottery(lottery)
        }
        for (item in list) {
            item.lotId = lottery.id
            lmsLotteryDao.saveLotteryDetail(item)
        }
    }
}