package cn.thens.kdroid.core.io

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

    fun toHex(): Codec<String, ByteArray> {
        return object : Codec<String, ByteArray> {
            override fun encode(plain: String): ByteArray {
                if (plain.isEmpty()) return ByteArray(0)
                val length = plain.length
                val result = ByteArray(length / 2)
                var i = 0
                while (i < length - 1) {
                    result[i / 2] = plain.substring(i, i + 2).toByte(16)
                    i += 2
                }
                return result
            }

            override fun decode(cipher: ByteArray): String {
                val builder = StringBuilder()
                for (b in cipher) {
                    var hex = Integer.toHexString(b.toInt() and 0xFF)
                    if (hex.length == 1) hex = "0$hex"
                    builder.append(hex)
                }
                return builder.toString()
            }
        }
    }

    fun toBase16(): Codec<String, ByteArray> = toHex()

    fun toBase64(flags: Int = Base64.NO_WRAP): Codec<String, ByteArray> {
        return toByteArray()
                .then(object : Codec<ByteArray, ByteArray> {
                    override fun encode(plain: ByteArray): ByteArray {
                        return Base64.encode(plain, flags)
                    }

                    override fun decode(cipher: ByteArray): ByteArray {
                        return Base64.decode(cipher, flags);
                    }
                })
    }

    /**
     * decodes characters except `[0-9A-Za-z_\-\.\*~\(\)\!']` as '%'-escaped octets using the UTF-8 scheme.
     */
    fun toUri() = Codec.create(Uri::encode, Uri::decode)

    /**
     * decodes characters except `[^0-9A-Za-z_\-\.\*]` as '%'-escaped octets
     * For example: '#' -> %23. In addition, spaces are substituted by '+'.
     */
    fun toUrl(charset: Charset = Charsets.UTF_8): Codec<String, String> {
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

    fun encryptToHex(crypto: Crypto): Codec<String, String> {
        return toByteArray()
                .then(crypto.toCodec())
                .then(toHex().inverted())
    }
}