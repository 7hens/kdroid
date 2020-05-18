package cn.thens.kdroid.io

import java.security.KeyFactory
import java.security.spec.X509EncodedKeySpec
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

interface Crypto {
    fun encrypt(bytes: ByteArray): ByteArray
    fun decrypt(bytes: ByteArray): ByteArray

    fun toCodec(): Codec<ByteArray, ByteArray> {
        return object : Codec<ByteArray, ByteArray> {
            override fun encode(plain: ByteArray): ByteArray {
                return encrypt(plain)
            }

            override fun decode(cipher: ByteArray): ByteArray {
                return decrypt(cipher)
            }
        }
    }

    companion object {
        private val DEFAULT_IV = "0123456789abcdef".toByteArray()

        private fun cipher(crypt: (mode: Int, bytes: ByteArray) -> ByteArray): Crypto {
            return object : Crypto {
                override fun encrypt(bytes: ByteArray): ByteArray {
                    return crypt(Cipher.ENCRYPT_MODE, bytes)
                }

                override fun decrypt(bytes: ByteArray): ByteArray {
                    return crypt(Cipher.DECRYPT_MODE, bytes)
                }
            }
        }

        /**
         * | 算法/模式/填充                 |   16字节加密后数据长度  |    不满16字节加密后长度 |
         * | ------------------------------ | ------- --------------- | ----------------------- |
         * | AES/CBC/NoPadding              |   16                    |    不支持               |
         * | AES/CBC/PKCS5Padding           |   32                    |    16                   |
         * | AES/CBC/ISO10126Padding        |   32                    |    16                   |
         * | AES/CFB/NoPadding              |   16                    |     原始数据长度        |
         * | AES/CFB/PKCS5Padding           |   32                    |     16                  |
         * | AES/CFB/ISO10126Padding        |   32                    |     16                  |
         * | AES/ECB/NoPadding              |   16                    |     不支持              |
         * | AES/ECB/PKCS5Padding           |   32                    |     16                  |
         * | AES/ECB/ISO10126Padding        |   32                    |     16                  |
         * | AES/OFB/NoPadding              |   16                    |     原始数据长度        |
         * | AES/OFB/PKCS5Padding           |   32                    |     16                  |
         * | AES/OFB/ISO10126Padding        |   32                    |     16                  |
         * | AES/PCBC/NoPadding             |   16                    |     不支持              |
         * | AES/PCBC/PKCS5Padding          |   32                    |     16                  |
         * | AES/PCBC/ISO10126Padding       |   32                    |     16                  |
         */
        fun aes(secret: ByteArray, transformation: String = "AES/CBC/PKCS5Padding", iv: ByteArray = DEFAULT_IV): Crypto {
            val cipher = Cipher.getInstance(transformation)
            val key = SecretKeySpec(secret, "AES")
            val ivParam = IvParameterSpec(iv)
            return cipher { mode, bytes ->
                cipher.init(mode, key, ivParam)
                cipher.doFinal(bytes)
            }
        }

        fun rsa(secret: ByteArray, transformation: String = "RSA/ECB/PKCS1Padding"): Crypto {
            val cipher = Cipher.getInstance(transformation)
            val key = KeyFactory.getInstance("RSA").generatePublic(X509EncodedKeySpec(secret))
            return cipher { mode, bytes ->
                cipher.init(mode, key)
                cipher.doFinal(bytes)
            }
        }
    }
}
