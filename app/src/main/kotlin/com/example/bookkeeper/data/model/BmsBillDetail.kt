package com.example.bookkeeper.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal

@Entity(tableName = "bms_bill_detail")
class BmsBillDetail {
    /**
     * 主键
     */
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L

    /**
     * 账单ID
     */
    @ColumnInfo(name = "bill_id")
    var billId: Long? = null

    /**
     * 类型
     */
    var type: String? = null

    /**
     * 项
     */
    var item: Long? = null

    /**
     * 金额
     */
    var price: BigDecimal? = null

    /**
     * 群
     */
    var cluster: Int? = null

    /**
     * 审核状态
     */
    @ColumnInfo(name = "check_status")
    var checkStatus: String? = null
}
