package com.example.bookkeeper.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "lms_lottery")
class LmsLottery {
    /**
     * 主键
     */
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L

    /**
     * 名称
     */
    var name: String? = null

    /**
     * 地区
     */
    var area: String? = null

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
