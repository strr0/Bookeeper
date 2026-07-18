package com.example.bookkeeper.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.RawQuery
import androidx.sqlite.db.SupportSQLiteQuery
import com.example.bookkeeper.data.model.AmsAccount
import com.example.bookkeeper.data.model.BmsBill
import com.example.bookkeeper.data.vo.BmsBillDetailVo
import com.example.bookkeeper.data.vo.BmsBillVo
import kotlinx.coroutines.flow.Flow

@Dao
interface BmsBillDao {
    @RawQuery(observedEntities = [AmsAccount::class, BmsBill::class])
    fun listBills(query: SupportSQLiteQuery): Flow<List<BmsBillVo>>

    @Query("select t1.*,t2.update_time " +
            "from bms_bill_detail t1 " +
            "inner join bms_bill t2 on t1.bill_id=t2.id " +
            "where bill_id=:billId")
    fun listBillDetails(billId: Long): Flow<List<BmsBillDetailVo>>
}