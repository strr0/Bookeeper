package com.example.bookkeeper.data.vo

import androidx.room.ColumnInfo

class LmsLotteryDetailVo {
    /**
     * 主键
     */
    var id: Int = 0

    /**
     * 彩票ID
     */
    @ColumnInfo(name = "lot_id")
    var lotId: Int? = null

    /**
     * 数字
     */
    var num: Int? = null

    /**
     * 生肖
     */
    var zod: Int? = null

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
