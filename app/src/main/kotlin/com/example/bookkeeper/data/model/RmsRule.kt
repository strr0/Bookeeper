package com.example.bookkeeper.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal
import java.util.Date

@Entity(tableName = "rms_rule")
class RmsRule {
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
     * 匹配次数
     */
    var match: Int? = null

    /**
     * 倍率
     */
    var times: BigDecimal? = null

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
