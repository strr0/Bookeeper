package com.example.bookkeeper.data.vo

import androidx.room.ColumnInfo

class LmsLotteryDetailVo {
    /**
     * 主键
     */
    var id: Long = 0L

    /**
     * 彩票ID
     */
    @ColumnInfo(name = "lot_id")
    var lotId: Long? = null

    /**
     * 数字
     */
    var num: Long? = null

    /**
     * 生肖
     */
    var zod: Long? = null

    /**
     * 排序
     */
    var seq: Int? = null

    /**
     * 地区
     */
    var area: String? = null

    /**
     * 颜色
     */
    var color: String? = null
}
