package cn.thens.kdroid.io

import android.net.Uri
import android.util.Base64
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.Charset

@Suppress("unused")
object StringCodec {
    fun toBoolean() = Codec.create(String::toBoolean, Boolean::toString)

    fun toShort() = Codec.create(String::toShort, Short::toString)

    fun toInt() = Codec.create(String::toInt, Int::toString)

    fun toLong() = Codec.create(String::toLong, Long::toString)

    fun toFloat() = Codec.create(String::toFloat, Float::toString)

    fun toDouble() = Codec.create(String::toDouble, Double::toString)

    fun toCharArray() = Codec.create(String::toCharArray, CharArray::toString)

    fun toByteArray(charset: Charset = Charsets.UTF_8): Codec<String, ByteArray> {
        return object : Codec<String, ByteArray> {
            override fun decode(cipher: ByteArray): String {
                return String(cipher, charset)
            }

            override fun encode(plain: String): ByteArray {
                return plain.toByteArray(charset)
            }
        }
    }

    inline fun <reified T> json(json: Json = Json): Codec<String, T> {
        return json.toCodec()
    }

    fun hex(): Codec<ByteArray, String> {
        return object : Codec<ByteArray, String> {
            override fun encode(plain: ByteArray): String {
                return plain.joinToString("") { "%02x".format(it) }
            }

            override fun decode(cipher: String): ByteArray {
                if (cipher.isEmpty()) return ByteArray(0)
                val result = ByteArray(cipher.length / 2)
                for (i in cipher.indices.step(2)) {
                    result[i / 2] = cipher.substring(i, i + 2).toInt(16).toByte()
                }
                return result
            }
        }
    }

    fun base16() = hex()

    fun base64(flags: Int = Base64.NO_WRAP or Base64.NO_PADDING or Base64.URL_SAFE): Codec<ByteArray, String> {
        return object : Codec<ByteArray, String> {
            override fun encode(plain: ByteArray): String {
                return Base64.encodeToString(plain, flags)
            }

            override fun decode(cipher: String): ByteArray {
                return Base64.decode(cipher, flags)
            }
        }
    }

    /**
     * decodes characters except `[0-9A-Za-z_\-\.\*~\(\)\!']` as '%'-escaped octets using the UTF-8 scheme.
     */
    fun uri() = Codec.create(Uri::encode, Uri::decode)

    /**
     * decodes characters except `[^0-9A-Za-z_\-\.\*]` as '%'-escaped octets
     * For example: '#' -> %23. In addition, spaces are substituted by '+'.
     */
    fun url(charset: Charset = Charsets.UTF_8): Codec<String, String> {
        val charsetName = charset.name()
        return object : Codec<String, String> {
            override fun decode(cipher: String): String {
                return URLDecoder.decode(cipher, charsetName)
            }

            override fun encode(plain: String): String {
                return URLEncoder.encode(plain, charsetName)
            }
        }
    }
}