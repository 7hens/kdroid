package cn.thens.kdroid.app

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.os.Build
import android.os.Bundle
import android.util.SparseArray
import androidx.annotation.RequiresApi
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.concurrent.atomic.AtomicInteger
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

/**
 * @author 7hens
 */
@Suppress("MemberVisibilityCanBePrivate", "unused")
abstract class ActivityRequest {
    open fun getSteppingComponent(context: Context): ComponentName {
        return ComponentName(context, SteppingActivity::class.java)
    }

    protected abstract fun startActivityForResult(context: Activity, requestCode: Int)

    fun run(context: Context, callback: Callback = EMPTY_CALLBACK) {
        requestCodeProvider.compareAndSet(Int.MAX_VALUE, 0)
        val requestCode = requestCodeProvider.incrementAndGet()
        try {
            requestMap.put(requestCode, this)
            responseMap.put(requestCode, callback)
            context.startActivity(Intent()
                    .setComponent(getSteppingComponent(context))
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    .putExtra(EXTRA_REQUEST_CODE, requestCode))
        } catch (e: Throwable) {
            requestMap.delete(requestCode)
            responseMap.delete(requestCode)
            callback.onError(e)
        }
    }

    suspend fun run(context: Context): Result {
        return suspendCancellableCoroutine {
            run(context, object : Callback {
                override fun onError(e: Throwable) {
                    it.resumeWithException(e)
                }

                override fun onSuccess(code: Int, data: Intent) {
                    it.resume(Result(code, data))
                }
            })
        }
    }

    data class Result(val code: Int, val data: Intent)

    companion object {
        private const val EXTRA_REQUEST_CODE = "REQUEST_CODE"
        private val requestMap = SparseArray<ActivityRequest>()
        private val responseMap = SparseArray<Callback>()
        private val requestCodeProvider = AtomicInteger()
        private val EMPTY_CALLBACK = object : Callback {}

        @JvmStatic
        fun doRequest(activity: Activity) {
            val requestCode = activity.intent.getIntExtra(EXTRA_REQUEST_CODE, 0)
            val request = requestMap.pick(requestCode)
            try {
                request!!.startActivityForResult(activity, requestCode)
            } catch (e: Exception) {
                activity.finish()
                responseMap.pick(requestCode)?.onError(e)
            }
        }

        @JvmStatic
        fun onResult(activity: Activity, requestCode: Int, resultCode: Int, data: Intent?) {
            activity.finish()
            responseMap.pick(requestCode)?.onSuccess(resultCode, data ?: Intent())
        }

        private fun <E> SparseArray<E>.pick(requestCode: Int): E? {
            val element: E? = this[requestCode]
            delete(requestCode)
            return element
        }

        inline fun create(crossinline fn: Activity.(requestCode: Int) -> Unit): ActivityRequest {
            return object : ActivityRequest() {
                override fun startActivityForResult(context: Activity, requestCode: Int) {
                    context.fn(requestCode)
                }
            }
        }

        @JvmStatic
        fun create(intent: Intent): ActivityRequest {
            return create { requestCode -> startActivityForResult(intent, requestCode) }
        }

        @JvmStatic
        @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
        fun create(intent: Intent, options: Bundle?): ActivityRequest {
            return create { requestCode -> startActivityForResult(intent, requestCode, options) }
        }

        @JvmStatic
        fun create(intent: IntentSender, fillInIntent: Intent, flagsMask: Int, flagsValues: Int, extraFlags: Int): ActivityRequest {
            return create { requestCode ->
                startIntentSenderForResult(intent, requestCode, fillInIntent, flagsMask, flagsValues, extraFlags)
            }
        }

        @JvmStatic
        @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
        fun create(intent: IntentSender, fillInIntent: Intent, flagsMask: Int, flagsValues: Int, extraFlags: Int, options: Bundle?): ActivityRequest {
            return create { requestCode ->
                startIntentSenderForResult(intent, requestCode, fillInIntent, flagsMask, flagsValues, extraFlags, options)
            }
        }
    }

    interface Callback {
        fun onError(e: Throwable) {}
        fun onSuccess(code: Int, data: Intent) {}
    }

    class SteppingActivity : Activity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            doRequest(this)
        }

        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            onResult(this, requestCode, resultCode, data)
        }
    }
}
