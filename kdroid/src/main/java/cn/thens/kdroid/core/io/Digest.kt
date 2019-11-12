package cn.thens.kdroid.core.io

import java.io.ByteArrayInputStream
import java.io.File
import java.io.InputStream
import java.security.MessageDigest

interface Digest {
    fun hash(input: InputStream): ByteArray

    fun hash(bytes: ByteArray): ByteArray {
        return ByteArrayInputStream(bytes).use { hash(it) }
    }

    fun hash(file: File): ByteArray {
        return file.inputStream().use { hash(it) }
    }

    companion object {
        fun of(algorithm: String): Digest {
            return object : Digest {
                override fun hash(input: InputStream): ByteArray {
                    val digest = MessageDigest.getInstance(algorithm)
                    val buffer = ByteArray(8 * 1024)
                    var length = input.read(buffer)
                    while (length != -1) {
                        digest.update(buffer, 0, length)
                        length = input.read(buffer)
                    }
                    return digest.digest()
                }
            }
        }

        val MD5 by lazy { of("MD5") }
        val SHA1 by lazy { of("SHA1") }
        val SHA256 by lazy { of("SHA256") }
    }
}