package cn.thens.kdroid.app

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.core.content.FileProvider
import java.io.File

@Suppress("unused")
object ApkInstaller {
    fun install(context: Context, file: File) {
        val intent = Intent(Intent.ACTION_VIEW).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val uriForFile = FileProvider.getUriForFile(context, context.packageName + ".provider", file)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            intent.setDataAndType(uriForFile, context.contentResolver.getType(uriForFile))
        } else {
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive")
        }
        context.startActivity(intent)
    }
}