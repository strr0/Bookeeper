package com.example.bookkeeper.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.bookkeeper.data.vo.DmsDigitVo
import com.example.bookkeeper.data.vo.DmsZodiacVo
import kotlinx.coroutines.flow.Flow

@Dao
interface DmsDigitDao {
    @Query("select * from dms_digit")
    suspend fun listAllDigits(): List<DmsDigitVo>

    @Query("select t1.item,sum(t1.price) amount " +
            "from bms_bill_detail t1 " +
            "inner join bms_bill t2 on t1.bill_id=t2.id " +
            "where t2.update_time between :beginTime and :endTime and t2.area=:area and t1.type='1' " +
            "group by t1.item")
    fun listDigits(beginTime: Long, endTime: Long, area: String): Flow<List<DmsDigitVo>>

    @Query("select * from dms_zodiac")
    suspend fun listAllZodiacs(): List<DmsZodiacVo>

    @Query("select t1.item,sum(t1.price) amount " +
            "from bms_bill_detail t1 " +
            "inner join bms_bill t2 on t1.bill_id=t2.id " +
            "where t2.update_time between :beginTime and :endTime and t2.area=:area and t1.type='2' " +
            "group by t1.item")
    fun listZodiacs(beginTime: Long, endTime: Long, area: String): Flow<List<DmsZodiacVo>>
}