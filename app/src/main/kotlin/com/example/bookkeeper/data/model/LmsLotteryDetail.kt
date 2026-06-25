package com.example.bookkeeper.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "lms_lottery_detail")
class LmsLotteryDetail {
    /**
     * 主键
     */
    @PrimaryKey(autoGenerate = true)
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
}
