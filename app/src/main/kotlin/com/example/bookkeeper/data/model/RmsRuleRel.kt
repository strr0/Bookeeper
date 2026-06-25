package com.example.bookkeeper.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "rms_rule_rel", primaryKeys = ["rule_id", "acc_id"])
class RmsRuleRel {
    /**
     * 规则ID
     */
    @ColumnInfo(name = "rule_id")
    var ruleId: Int = 0

    /**
     * 账户ID
     */
    @ColumnInfo(name = "acc_id")
    var accId: Int = 0

    /**
     * 倍率
     */
    var times: String? = null
}
