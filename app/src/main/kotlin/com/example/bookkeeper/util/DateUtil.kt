package com.example.bookkeeper.util

import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*

class DateUtil {
    companion object {
        val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val oneDayMillis = 86399999L

        fun formatDate(): String? {
            return formatDate(LocalDate.now())
        }

        fun formatDate(date: LocalDate): String? {
            return date.format(dateFormatter)
        }

        fun formatDateTime(): String? {
            return formatDateTime(LocalDateTime.now())
        }

        fun formatDateTime(datetime: LocalDateTime): String? {
            return datetime.format(dateTimeFormatter)
        }

        fun formatDateTime(date: Date?): String? {
            return date?.let { simpleDateFormat.format(it) }
        }

        fun parseDate(string: String): LocalDate? {
            return LocalDate.parse(string, dateFormatter)
        }

        fun parseDateTime(string: String): LocalDateTime? {
            return LocalDateTime.parse(string, dateTimeFormatter)
        }

        fun getTimestamp(date: LocalDate): Long {
            return getTimestamp(date.atStartOfDay())
        }

        fun getTimestamp(datetime: LocalDateTime): Long {
            return getTimestamp(datetime, ZoneId.systemDefault())
        }

        fun getTimestamp(datetime: LocalDateTime, zoneId: ZoneId): Long {
            return datetime.atZone(zoneId).toInstant().toEpochMilli()
        }

        fun getSystemTimestamp(timestamp: Long): Long {
            return timestamp - TimeZone.getDefault().rawOffset
        }

        fun getAndroidTimestamp(date: LocalDate): Long {
            return getTimestamp(date.atStartOfDay(), ZoneOffset.UTC)
        }

        fun getAndroidTimestamp(string: String, formatter: DateTimeFormatter): Long {
            val date = LocalDate.parse(string, formatter)
            return getTimestamp(date.atStartOfDay(), ZoneOffset.UTC)
        }

        fun getEndDayTimestamp(timestamp: Long): Long {
            return timestamp + oneDayMillis
        }

        fun getDate(timestamp: Long): LocalDate {
            return Instant.ofEpochMilli(timestamp).atZone(ZoneId.systemDefault()).toLocalDate()
        }
    }
}