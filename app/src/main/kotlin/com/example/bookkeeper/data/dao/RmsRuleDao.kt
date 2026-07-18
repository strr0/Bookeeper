package com.example.bookkeeper.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.bookkeeper.data.model.RmsRule
import com.example.bookkeeper.data.vo.RmsRuleVo
import kotlinx.coroutines.flow.Flow

@Dao
interface RmsRuleDao {
    @Query("select * from rms_rule")
    fun listAllRules(): Flow<List<RmsRule>>

    @Query("select t1.id,t2.acc_id,t1.name,t1.`match`,ifnull(t2.times,t1.times) times,t1.update_time,t1.status from rms_rule t1 left join rms_rule_rel t2 on t1.id=t2.rule_id and t2.acc_id=:accId")
    fun listAccRules(accId: Long): Flow<List<RmsRuleVo>>
}