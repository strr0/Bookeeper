package com.example.bookkeeper.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.bookkeeper.data.vo.BmsBillVo
import kotlinx.coroutines.flow.Flow

@Dao
interface BmsBillDao {
    @Query("select t1.*,t2.name acc_name " +
            "from bms_bill t1 " +
            "inner join ams_account t2 on t1.acc_id=t2.id " +
            "where t1.update_time between :beginTime and :endTime " +
            "order by t1.update_time desc")
    fun listBills(beginTime: Long, endTime: Long): Flow<List<BmsBillVo>>
}