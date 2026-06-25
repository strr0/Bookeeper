package com.example.bookkeeper.data.vo

import androidx.room.ColumnInfo
import java.math.BigDecimal
import java.util.*

class DmsZodiacVo {
    /**
     * 主键
     */
    var id: Int? = null

    /**
     * 编码
     */
    var code: String? = null

    /**
     * 名称
     */
    var name: String? = null

    /**
     * 图片
     */
    var picture: String? = null

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
