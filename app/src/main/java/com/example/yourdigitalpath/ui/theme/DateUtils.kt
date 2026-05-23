package com.example.yourdigitalpath.ui.theme

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

object DateUtils {
    private const val PICKER_PATTERN = "yyyy / MM / dd"
    private const val DISPLAY_PATTERN = "dd MMMM yyyy"
    private const val FULL_DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss"
    private const val DATE_ONLY_PATTERN = "yyyy-MM-dd"
    private const val TIME_ONLY_PATTERN = "hh:mm a"
    private const val DATETIME_SHORT_PATTERN = "yyyy-MM-dd HH:mm"

    fun formatOrderDate(timestamp: Long): String {
        val locale = Locale.getDefault()
        val formatter = SimpleDateFormat(DISPLAY_PATTERN, locale)
        return formatter.format(Date(timestamp))
    }

    fun formatDateForPicker(timestamp: Long): String {
        val format = SimpleDateFormat(PICKER_PATTERN, Locale.getDefault())
        format.timeZone = TimeZone.getTimeZone("UTC")
        return format.format(Date(timestamp))
    }

    fun parseDateFromPicker(dateString: String): Long? {
        return try {
            val format = SimpleDateFormat(PICKER_PATTERN, Locale.getDefault())
            format.timeZone = TimeZone.getTimeZone("UTC")
            format.parse(dateString)?.time
        } catch (e: Exception) {
            null
        }
    }

    fun formatFullDateTime(date: Date = Date()): String {
        return SimpleDateFormat(FULL_DATETIME_PATTERN, Locale.getDefault()).format(date)
    }

    fun formatShortDateTime(date: Date = Date()): String {
        return SimpleDateFormat(DATETIME_SHORT_PATTERN, Locale.getDefault()).format(date)
    }

    fun formatIsoDate(date: Date = Date()): String {
        return SimpleDateFormat(DATE_ONLY_PATTERN, Locale.getDefault()).format(date)
    }

    fun formatTime(date: Date = Date()): String {
        return SimpleDateFormat(TIME_ONLY_PATTERN, Locale.getDefault()).format(date)
    }

    fun parseDisplayDate(dateString: String): Date? {
        return try {
            SimpleDateFormat(DISPLAY_PATTERN, Locale.getDefault()).parse(dateString)
        } catch (e: Exception) {
            null
        }
    }

    fun isDateInPastOrPresent(utcTimeMillis: Long): Boolean {
        return utcTimeMillis <= System.currentTimeMillis()
    }

    fun isYearInPastOrPresent(year: Int): Boolean {
        return year <= Calendar.getInstance().get(Calendar.YEAR)
    }
}
