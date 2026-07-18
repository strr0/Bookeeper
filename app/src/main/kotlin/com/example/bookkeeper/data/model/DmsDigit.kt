package com.example.bookkeeper.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "dms_digit")
class DmsDigit {
    /**
     * 主键
     */
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L

    /**
     * 数字
     */
    var num: Long? = null

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
}
