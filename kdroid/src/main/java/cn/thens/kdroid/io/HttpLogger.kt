package cn.thens.kdroid.io

import cn.thens.kdroid.util.Logdog
import okhttp3.logging.HttpLoggingInterceptor

class HttpLogger : HttpLoggingInterceptor.Logger {
    private val mMessage = StringBuilder()
    //    private Pattern fistLine = Pattern.compile("--> (POST|GET|PUT|DELETE|HEAD)");

    override fun log(message: String) {
        val isJson =
            message.run { startsWith("{") && endsWith("}") || startsWith("[") && endsWith("]") }
        val formattedMessage = if (isJson) formatJson(decodeUnicode(message)) else message
        mMessage.append(formattedMessage + "\n")
        if (formattedMessage.startsWith("<-- END HTTP") || formattedMessage.startsWith("<-- HTTP FAILED")) {
            Logdog.debug(mMessage.toString())
            mMessage.setLength(0)
        }
    }


    /**
     * 格式化json字符串
     *
     * @param jsonStr 需要格式化的json串
     * @return 格式化后的json串
     */
    private fun formatJson(jsonStr: String?): String {
        if (null == jsonStr || "" == jsonStr) return ""
        val sb = StringBuilder()
        var last: Char
        var current = '\u0000'
        var indent = 0
        for (element in jsonStr) {
            last = current
            current = element
            //遇到{ [换行，且下一行缩进
            when (current) {
                '{', '[' -> {
                    sb.append(current)
                    sb.append('\n')
                    indent++
                    addIndentBlank(sb, indent)
                }
                //遇到} ]换行，当前行缩进
                '}', ']' -> {
                    sb.append('\n')
                    indent--
                    addIndentBlank(sb, indent)
                    sb.append(current)
                }
                //遇到,换行
                ',' -> {
                    sb.append(current)
                    if (last != '\\') {
                        sb.append('\n')
                        addIndentBlank(sb, indent)
                    }
                }
                else -> sb.append(current)
            }
        }
        return sb.toString()
    }

    /**
     * 添加space
     *
     * @param sb
     * @param indent
     */
    private fun addIndentBlank(sb: StringBuilder, indent: Int) {
        for (i in 0 until indent) {
            sb.append('\t')
        }
    }

    /**
     * http 请求数据返回 json 中中文字符为 unicode 编码转汉字转码
     *
     * @param theString
     * @return 转化后的结果.
     */
    fun decodeUnicode(theString: String): String {
        var aChar: Char
        val len = theString.length
        val outBuffer = StringBuilder(len)
        var x = 0
        while (x < len) {
            aChar = theString[x++]
            if (aChar == '\\') {
                aChar = theString[x++]
                if (aChar == 'u') {
                    var value = 0
                    for (i in 0..3) {
                        aChar = theString[x++]
                        value = when (aChar) {
                            in '0'..'9' -> (value shl 4) + aChar.toInt() - '0'.toInt()
                            in 'a'..'f' -> (value shl 4) + 10 + aChar.toInt() - 'a'.toInt()
                            in 'A'..'F' -> (value shl 4) + 10 + aChar.toInt() - 'A'.toInt()
                            else -> throw IllegalArgumentException("Malformed   \\uxxxx   encoding.")
                        }

                    }
                    outBuffer.append(value.toChar())
                } else {
                    when (aChar) {
                        't' -> aChar = '\t'
                        'r' -> aChar = '\r'
                        'n' -> aChar = '\n'
                    }
                    outBuffer.append(aChar)
                }
            } else
                outBuffer.append(aChar)
        }
        return outBuffer.toString()
    }
}