package com.example.bookkeeper.data.repository

import android.app.Application
import androidx.sqlite.db.SimpleSQLiteQuery
import com.example.bookkeeper.data.dao.AmsAccountDao
import com.example.bookkeeper.data.dao.AppDatabase
import com.example.bookkeeper.data.dao.BmsBillDao
import com.example.bookkeeper.data.model.AmsAccount
import com.example.bookkeeper.data.vo.BmsBillDetailVo
import com.example.bookkeeper.data.vo.BmsBillVo
import com.example.bookkeeper.util.DateUtil
import kotlinx.coroutines.flow.Flow

class DiaryRepository(application: Application) {
    private val _amsAccountDao: AmsAccountDao? = AppDatabase.getInstance(application).amsAccountDao()
    private val _bmsBillDao: BmsBillDao? = AppDatabase.getInstance(application).bmsBillDao()
    private val amsAccountDao: AmsAccountDao = _amsAccountDao!!
    private val bmsBillDao: BmsBillDao = _bmsBillDao!!

    fun listBills(date: Long, accId: Int, area: String): Flow<List<BmsBillVo>> {
        val args = mutableListOf<Any>()
        val sql = StringBuilder("select t1.*,t2.name acc_name from bms_bill t1 inner join ams_account t2 on t1.acc_id=t2.id where 1=1 ")
        if (date != 0L) {
            sql.append("and t1.update_time between ? and ? ")
            args.add(date)
            args.add(DateUtil.getEndDayTimestamp(date))
        }
        if (accId != 0) {
            sql.append("and t1.acc_id = ? ")
            args.add(accId)
        }
        if (area != "") {
            sql.append("and t1.area = ? ")
            args.add(area)
        }
        sql.append("order by t1.update_time desc")
        val query = SimpleSQLiteQuery(sql.toString(), args.toTypedArray())
        return bmsBillDao.listBills(query)
    }

    fun listAllAccounts(): Flow<List<AmsAccount>> = amsAccountDao.listAllAccounts()

    fun listBillDetails(billId: Int): Flow<List<BmsBillDetailVo>> = bmsBillDao.listBillDetails(billId)
}