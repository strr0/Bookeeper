package com.example.bookkeeper.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.bookkeeper.data.model.LmsLottery
import com.example.bookkeeper.data.model.LmsLotteryDetail
import com.example.bookkeeper.data.vo.LmsLotteryDetailVo
import kotlinx.coroutines.flow.Flow

@Dao
interface LmsLotteryDao {
    @Query("select t1.*,t2.area,t3.color " +
            "from lms_lottery_detail t1 " +
            "inner join lms_lottery t2 on t1.lot_id=t2.id " +
            "left join dms_digit t3 on t1.num=t3.num " +
            "where t2.update_time between :beginTime and :endTime and t2.area=:area " +
            "order by t1.seq")
    fun listLotteryDetails(beginTime: Long, endTime: Long, area: String): Flow<List<LmsLotteryDetailVo>>

    @Query("select * from lms_lottery where update_time between :beginTime and :endTime and area=:area")
    suspend fun getLottery(beginTime: Long, endTime: Long, area: String): LmsLottery?

    @Insert
    suspend fun saveLottery(record: LmsLottery): Long

    @Insert
    suspend fun saveLotteryDetail(record: LmsLotteryDetail)
}