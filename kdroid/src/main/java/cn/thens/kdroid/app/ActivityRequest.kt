package cn.thens.kdroid.app

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.SparseArray
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class ActivityRequest(private val context: Context) {
    @Suppress("UNCHECKED_CAST")
    private suspend fun <T> request(fn: Fragment.(requestCode: Int) -> Unit): T {
        return suspendCoroutine<Any> { Contract(fn, it).launch(context) } as T
    }

    suspend fun start(intent: Intent, options: Bundle? = null): Result {
        return request { startActivityForResult(intent, it, options) }
    }

    suspend fun start(intent: IntentSender, fillInIntent: Intent, flagsMask: Int,
                      flagsValues: Int, extraFlags: Int, options: Bundle? = null): Result {
        return request {
            startIntentSenderForResult(intent, it, fillInIntent, flagsMask,
                    flagsValues, extraFlags, options)
        }
    }

    suspend fun permissions(vararg permissions: String): Map<String, Boolean> {
        return request { requestPermissions(permissions, it) }
    }

    suspend fun permission(permission: String): Boolean {
        return permissions(permission)[permission] == true
    }

    private data class Contract(val request: Fragment.(requestCode: Int) -> Unit,
                                val continuation: Continuation<Any>) {
        fun launch(context: Context) {
            val requestCode = ViewCompat.generateViewId()
            try {
                contracts.put(requestCode, this)
                if (context is FragmentActivity) {
                    context.supportFragmentManager.apply {
                        request(findFragmentByTag(FRAGMENT_TAG) ?: BridgeFragment().also {
                            beginTransaction().add(it, FRAGMENT_TAG).commitNow()
                        }, requestCode)
                    }
                } else {
                    context.startActivity(Intent(context, BridgeActivity::class.java)
                            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            .putExtra(REQUEST_CODE, requestCode))
                }
            } catch (e: Throwable) {
                contracts.remove(requestCode)
                continuation.resumeWithException(e)
            }
        }
    }

    data class Result(val code: Int, val data: Intent)

    companion object {
        private const val FRAGMENT_TAG = "kdroid.ActivityRequest"
        private const val REQUEST_CODE = "REQUEST_CODE"
        private val contracts = SparseArray<Contract>()
    }

    class BridgeActivity : FragmentActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            launch(intent)
        }

        override fun onNewIntent(intent: Intent?) {
            super.onNewIntent(intent)
            launch(intent!!)
        }

        private fun launch(intent: Intent) {
            val requestCode = intent.getIntExtra(REQUEST_CODE, 0)
            contracts[requestCode]!!.launch(this)
        }
    }

    private class BridgeFragment : Fragment() {
        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            onResult(requestCode, Result(resultCode, data ?: Intent()))
        }

        override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
            onResult(requestCode, mutableMapOf<String, Boolean>().also {
                permissions.forEachIndexed { index, permission ->
                    it[permission] = grantResults[index] == PackageManager.PERMISSION_GRANTED
                }
            })
        }

        private fun onResult(requestCode: Int, result: Any) {
            (context as? BridgeActivity)?.moveTaskToBack(true)
            contracts.get(requestCode)?.continuation?.resume(result)
            contracts.remove(requestCode)
        }
    }
}