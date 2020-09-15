package cn.thens.kdroid

import android.app.Application
import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.net.Uri
import cn.thens.kdroid.app.TopActivity
import java.util.concurrent.atomic.AtomicBoolean

object KDroid {
    private val isInitialized = AtomicBoolean(false)

    val debug: Boolean by lazy {
        try {
            val cBuildConfig = Class.forName(app.packageName + ".BuildConfig")
            cBuildConfig.getField("DEBUG").get(null) as Boolean
        } catch (e: Exception) {
            false
        }
    }

    val app get() = application

    private lateinit var application: Application

    fun initialize(context: Context) {
        if (!isInitialized.compareAndSet(false, true)) return
        application = context.applicationContext as Application
        TopActivity.initialize(application)
    }

    class InitProvider : ContentProvider() {
        override fun onCreate(): Boolean {
            initialize(context!!)
            return true
        }

        override fun insert(uri: Uri, values: ContentValues?): Uri? {
            return null
        }

        override fun query(uri: Uri, projection: Array<String>?, selection: String?, selectionArgs: Array<String>?, sortOrder: String?): Cursor? {
            return null
        }
        override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<String>?): Int {
            return 0
        }

        override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
            return 0
        }

        override fun getType(uri: Uri): String? {
            return null
        }
    }
}