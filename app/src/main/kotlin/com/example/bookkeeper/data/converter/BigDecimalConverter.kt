package com.example.bookkeeper.data.converter

import androidx.room.TypeConverter
import java.math.BigDecimal

object BigDecimalConverter {
    @TypeConverter
    fun fromVarchar(value: String?): BigDecimal? = value?.let { BigDecimal(it) }

    @TypeConverter
    fun decimalToVarchar(decimal: BigDecimal?): String? = decimal?.stripTrailingZeros()?.toPlainString()
}
