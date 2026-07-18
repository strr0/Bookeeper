package com.example.bookkeeper.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "ums_user")
class UmsUser {
    /**
     * 主键
     */
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L

    /**
     * 用户名
     */
    var username: String? = null

    /**
     * 昵称
     */
    var nickname: String? = null

    /**
     * 密码
     */
    var password: String? = null

    /**
     * 盐值
     */
    var salt: String? = null

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