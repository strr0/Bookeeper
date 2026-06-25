package com.example.bookkeeper.data.converter

import androidx.room.TypeConverter
import java.util.*

object DateConverter {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? = value?.let { Date(it) }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? = date?.time
}
