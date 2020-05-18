package cn.thens.kdroid.io

import cn.thens.kdroid.util.Logdog
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlin.coroutines.CoroutineContext


@Suppress("FunctionName")
fun AndroidMainScope(): CoroutineScope {
    return object : CoroutineScope {
        override val coroutineContext = MainScope().coroutineContext + object : CoroutineExceptionHandler {
            override val key: CoroutineContext.Key<*> = CoroutineExceptionHandler

            override fun handleException(context: CoroutineContext, exception: Throwable) {
                Logdog.error(exception)
            }
        }
    }
}
