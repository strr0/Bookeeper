package com.example.bookkeeper.data.vo

import androidx.room.ColumnInfo
import java.math.BigDecimal
import java.util.*

class DmsDigitVo {
    /**
     * 主键
     */
    var id: Int? = null

    /**
     * 数字
     */
    var num: Int? = null

    /**
     * 生肖
     */
    var zod: Int? = null

    /**
     * 颜色
     */
    var color: String? = null

    /**
     * 开始时间
     */
    @ColumnInfo(name = "begin_time")
    var beginTime: Date? = null

    /**
     * 结束时间
     */
    @ColumnInfo(name = "end_time")
    var endTime: Date? = null

    /**
     * 更新时间
     */
    @ColumnInfo(name = "update_time")
    var updateTime: Date? = null

    /**
     * 状态
     */
    var status: String? = null

    /**
     * 地区
     */
    var area: String? = null

    /**
     * 金额
     */
    var amount: BigDecimal? = null
}
