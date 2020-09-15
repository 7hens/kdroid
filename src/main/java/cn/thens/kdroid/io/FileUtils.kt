package cn.thens.kdroid.io

import android.webkit.MimeTypeMap
import java.io.File
import java.net.URLConnection

object FileUtils {
    private const val UNKNOWN_MIME_TYPE = "application/octet-stream"

    fun getMimeType(file: File): String {
        val fileNameMap = URLConnection.getFileNameMap()
        return fileNameMap.getContentTypeFor(file.name) ?: UNKNOWN_MIME_TYPE
    }

    fun getMimeType2(file: File): String {
        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(file.extension)
            ?: UNKNOWN_MIME_TYPE
    }

    fun writeBytes(file: File, bytes: ByteArray) {
        file.outputStream().use { it.write(bytes) }
    }

    fun readBytes(file: File): ByteArray {
        return file.inputStream().use { it.readBytes() }
    }
}