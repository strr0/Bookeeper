package com.example.bookkeeper.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "dms_zodiac")
class DmsZodiac {
    /**
     * 主键
     */
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

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
}
