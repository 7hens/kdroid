package cn.thens.kdroid.app

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.SparseArray
import android.view.WindowManager
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import java.util.*
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

typealias FragmentRequest = Fragment.(requestCode: Int) -> Unit

data class ActivityResult(val code: Int, val data: Intent)

class ActivityRequest(private val context: Context) {
    @Suppress("UNCHECKED_CAST")
    suspend fun <T> start(request: FragmentRequest): T {
        val requestCode = ViewCompat.generateViewId()
        try {
            return suspendCoroutine<Any> { continuation ->
                val contract = Contract(requestCode, continuation, request)
                contracts.put(requestCode, contract)
                contract.launch(context)
            } as T
        } finally {
            contracts.remove(requestCode)
        }
    }

    suspend fun result(intent: Intent, options: Bundle? = null): ActivityResult {
        return start { startActivityForResult(intent, it, options) }
    }

    suspend fun result(
            intent: IntentSender, fillInIntent: Intent, flagsMask: Int,
            flagsValues: Int, extraFlags: Int, options: Bundle? = null): ActivityResult {
        return start {
            startIntentSenderForResult(intent, it, fillInIntent, flagsMask, flagsValues, extraFlags, options)
        }
    }

    suspend fun permissions(vararg permissions: String?): Map<String, Boolean> {
        return start { requestPermissions(permissions, it) }
    }

    companion object {
        private val contracts = SparseArray<Contract>()
        private val FRAGMENT_TAG: String? = "jack.ActivityRequest"
        private val REQUEST_CODE: String? = "jack.REQUEST_CODE"
    }

    private class Contract(val requestCode: Int, val continuation: Continuation<Any>, val request: FragmentRequest) {
        private var isRequested = false

        fun launch(context: Context) {
            if (isRequested) return
            try {
                if (context is FragmentActivity) {
                    isRequested = true
                    val fragmentManager = context.supportFragmentManager
                    var fragment = fragmentManager.findFragmentByTag(FRAGMENT_TAG)
                    if (fragment == null) {
                        fragment = ShadowFragment()
                        fragmentManager.beginTransaction()
                                .add(fragment, FRAGMENT_TAG)
                                .commitNow()
                    }
                    fragment.request(requestCode)
                } else {
                    context.startActivity(Intent(context, ShadowActivity::class.java)
                            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            .putExtra(REQUEST_CODE, requestCode)
                    )
                }
            } catch (e: Throwable) {
                continuation.resumeWithException(e)
            }
        }
    }

    class ShadowActivity : FragmentActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            overridePendingTransition(0, 0)
            window.attributes.flags = window.attributes.flags or WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            super.onCreate(savedInstanceState)
            launch(intent)
        }

        override fun onNewIntent(intent: Intent) {
            overridePendingTransition(0, 0)
            super.onNewIntent(intent)
            launch(intent)
        }

        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            super.onActivityResult(requestCode, resultCode, data)
            moveTaskToBack(true)
        }

        override fun finish() {
            super.finish()
            overridePendingTransition(0, 0)
        }

        private fun launch(intent: Intent) {
            val requestCode = intent.getIntExtra(REQUEST_CODE, 0)
            contracts[requestCode]?.launch(this)
        }
    }

    class ShadowFragment : Fragment() {
        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            super.onActivityResult(requestCode, resultCode, data)
            onResult(requestCode, ActivityResult(resultCode, data ?: Intent()))
        }

        override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            val result: MutableMap<String, Boolean> = HashMap()
            for (i in permissions.indices) {
                result[permissions[i]] = grantResults[i] == PackageManager.PERMISSION_GRANTED
            }
            onResult(requestCode, result)
        }

        private fun onResult(requestCode: Int, result: Any) {
            contracts[requestCode]?.continuation?.resume(result)
        }
    }
}