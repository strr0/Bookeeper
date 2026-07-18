package com.example.bookkeeper.data.vo

import androidx.room.ColumnInfo
import java.math.BigDecimal
import java.util.Date

class BmsBillDetailVo {
    /**
     * 主键
     */
    var id: Long? = null

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

    /**
     * 更新时间
     */
    @ColumnInfo(name = "update_time")
    var updateTime: Date? = null
}
