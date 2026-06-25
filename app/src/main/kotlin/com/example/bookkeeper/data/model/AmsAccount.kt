package com.example.bookkeeper.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal
import java.util.Date

@Entity(tableName = "ams_account")
class AmsAccount {
    /**
     * 主键
     */
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    /**
     * 名称
     */
    var name: String? = null

    /**
     * 头像
     */
    var avatar: String? = null

    /**
     * 电话
     */
    var phone: String? = null

    /**
     * 账户余额
     */
    var balance: BigDecimal? = null

    /**
     * 备注
     */
    var remarks: String? = null

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
