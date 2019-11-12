package cn.thens.kdroid.core.util

object Numbers {
    private val CHINESE_LOWER_NUMBERS = arrayOf("零", "一", "二", "三", "四", "五", "六", "七", "八", "九")
    private val CHINESE_UPPER_NUMBERS = arrayOf("零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖")
    private val CHINESE_LOWER_DIGITS = arrayOf("", "十", "百", "千")
    private val CHINESE_UPPER_DIGITS = arrayOf("", "拾", "佰", "仟")
    private val CHINESE_RADIX_DIGITS = arrayOf("", "万", "亿", "兆", "京", "垓", "杼", "穰", "沟", "涧", "正", "载", "极", "恒河沙", "阿僧袛", "那由它", "不可思议", "无量", "大数", "古戈尔")
    private const val CHINESE_DOT = "点"

    fun chinease(n: Long, toUpper: Boolean = false): String {
        val numberRadix = CHINESE_LOWER_NUMBERS.size
        val digitRadix = CHINESE_LOWER_DIGITS.size
        val numbers = if (toUpper) CHINESE_UPPER_NUMBERS else CHINESE_LOWER_NUMBERS
        val digits = if (toUpper) CHINESE_UPPER_DIGITS else CHINESE_LOWER_DIGITS
        val radix = Math.pow(numberRadix.toDouble(), digitRadix.toDouble()).toLong()
        val buffer = StringBuilder()
        var temp = n
        if (temp == 0L) return numbers[0]
        var i = 0
        while (temp > 0) {
            val num = temp % radix
            temp /= radix
            if (num > 0) {
                buffer.insert(0, formatNumber(num, numbers, digits, temp == 0L).append(CHINESE_RADIX_DIGITS[i]))
            }
            i++
        }
        return buffer.toString()
    }

    private fun formatNumber(n: Long, numbers: Array<String>, digits: Array<String>, isFirst: Boolean): StringBuilder {
        val numberRadix = numbers.size
        val digitRadix = digits.size
        val buffer = StringBuilder()
        var temp = n
        var prevHasZero = false
        var alwaysZero = isFirst
        for (i in digitRadix - 1 downTo 0) {
            val radix = Math.pow(numberRadix.toDouble(), i.toDouble()).toLong()
            val num = (temp / radix).toInt()
            val isZero = num == 0
            if (!isZero) {
                if (prevHasZero && !alwaysZero) buffer.append(numbers[0])
                buffer.append(numbers[num]).append(digits[i])
                alwaysZero = false
            }
            temp %= radix
            prevHasZero = isZero
        }
        if (alwaysZero) buffer.append(numbers[0])
        return buffer
    }
}