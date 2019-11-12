package cn.thens.kdroid.core.io

import cn.thens.kdroid.core.util.Logdog
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlin.coroutines.CoroutineContext

/**
 * @author 7hens
 */
interface AndroidMainScope : CoroutineScope {
    override val coroutineContext get() = Companion.coroutineContext

    companion object : CoroutineScope {
        private val exceptionHandler = object : CoroutineExceptionHandler {
            override val key: CoroutineContext.Key<*> = CoroutineExceptionHandler

            override fun handleException(context: CoroutineContext, exception: Throwable) {
                Logdog.error(exception)
            }
        }

        override val coroutineContext = MainScope().coroutineContext + exceptionHandler
    }
}