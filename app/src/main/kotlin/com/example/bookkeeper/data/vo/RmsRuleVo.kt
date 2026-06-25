package com.example.bookkeeper.data.vo

import androidx.room.ColumnInfo
import java.math.BigDecimal
import java.util.*

class RmsRuleVo {
    /**
     * 主键
     */
    var id: Int = 0

    /**
     * 账户ID
     */
    @ColumnInfo(name = "acc_id")
    var accId: Int = 0

    /**
     * 名称
     */
    var name: String? = null

    /**
     * 匹配次数
     */
    var match: Int? = null

    /**
     * 倍率
     */
    var times: BigDecimal? = null

    /**
     * 更新时间
     */
    @ColumnInfo(name = "update_time")
    var updateTime: Date? = null

    /**
     * 状态
     */
    var status: String? = null
}