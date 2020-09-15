package cn.thens.kdroid.util

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

object Dates {
    private const val PATTERN_DEFAULT = "yyyy-MM-dd HH:mm:ss"

    val dayOfWeekInChinese: String get() = getDayOfWeekInChinese(System.currentTimeMillis())

    val dayOfWeek: Int get() = getDayOfWeek(System.currentTimeMillis())

    @JvmOverloads
    fun from(text: String, pattern: String = PATTERN_DEFAULT): Date? {
        val format = SimpleDateFormat(pattern, Locale.CHINA)
        var date: Date? = null
        try {
            date = format.parse(text)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return date
    }

    fun from(): Date {
        return Date()
    }

    @JvmOverloads
    fun format(pattern: String, date: Date = Date(), locale: Locale = Locale.CHINA): String {
        val formatter = SimpleDateFormat(pattern, locale)
        return formatter.format(date)
    }

    fun format(pattern: String, time: Long): String {
        return format(pattern, Date(time))
    }

    @JvmOverloads
    fun format(date: Date = Date()): String {
        return format(PATTERN_DEFAULT, date)
    }

    fun format(time: Long): String {
        return format(PATTERN_DEFAULT, time)
    }

    fun calendar(): Calendar {
        return Calendar.getInstance()
    }

    fun calendar(date: Date): Calendar {
        val calendar = calendar()
        calendar.time = date
        return calendar
    }

    fun calendar(time: Long): Calendar {
        val calendar = calendar()
        calendar.timeInMillis = time
        return calendar
    }

    fun smartFormat(calendar: Calendar): String {
        calendar.timeZone = TimeZone.getTimeZone("Etc/Greenwich")
        val day = TimeUnit.MILLISECONDS.toDays(calendar.timeInMillis)
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        val second = calendar.get(Calendar.SECOND)
        val buffer = StringBuilder()
        if (day > 0) buffer.append(day).append("天")
        if (hour > 0) buffer.append(hour).append("时")
        if (minute > 0) buffer.append(minute).append("分")
        if (second > 0) buffer.append(second).append("秒")
        return buffer.toString()
    }

    fun smartFormat(date: Date): String {
        return smartFormat(calendar(date))
    }

    fun smartFormat(time: Long): String {
        return smartFormat(calendar(time))
    }

    fun convertDayOfWeekToChinese(dayOfWeek: Int): String {
        when (dayOfWeek) {
            Calendar.SUNDAY -> return "周日"
            Calendar.MONDAY -> return "周一"
            Calendar.TUESDAY -> return "周二"
            Calendar.WEDNESDAY -> return "周三"
            Calendar.THURSDAY -> return "周四"
            Calendar.FRIDAY -> return "周五"
            Calendar.SATURDAY -> return "周六"
        }
        return ""
    }

    fun getDayOfWeekInChinese(timeMillis: Long): String {
        return convertDayOfWeekToChinese(getDayOfWeek(timeMillis))
    }

    fun getDayOfWeek(timeMills: Long): Int {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timeMills
        return calendar.get(Calendar.DAY_OF_WEEK)
    }

    fun isToday(timeMillis: Long): Boolean {
        return TimeUnit.MILLISECONDS.toDays(timeMillis) == TimeUnit.MILLISECONDS.toDays(System.currentTimeMillis())
    }
}