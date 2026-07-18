package com.example.bookkeeper.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal
import java.util.Date

@Entity(tableName = "bms_bill")
class BmsBill {
    /**
     * 主键
     */
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L

    /**
     * 账户ID
     */
    @ColumnInfo(name = "acc_id")
    var accId: Long? = null

    /**
     * 地区
     */
    var area: String? = null

    /**
     * 金额
     */
    var amount: BigDecimal? = null

    /**
     * 审核状态
     */
    @ColumnInfo(name = "check_status")
    var checkStatus: String? = null

    /**
     * 备注
     */
    var remarks: String? = null

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
