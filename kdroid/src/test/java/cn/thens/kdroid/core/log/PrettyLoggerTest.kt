package cn.thens.kdroid.core.log

import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class PrettyLoggerTest {
    @Test
    fun out() {
        val out = spy(Loggers.out())
        val logger = PrettyLogger(out)
        logger.log(ILogger.VERBOSE, "@", TEXT)
        logger.log(ILogger.DEBUG, "@", TEXT)
        logger.log(ILogger.INFO, "@", TEXT)
        logger.log(ILogger.WARN, "@", TEXT)
        logger.log(ILogger.ERROR, "@", TEXT)
        logger.log(ILogger.ASSERT, "@", TEXT)
        verify(out, atLeast(6)).log(anyInt(), anyString(), anyString())
    }

    companion object {
        const val TEXT = " String getCurProcessName(Context context) {\n" +
                "  int pid = android.os.Process.myPid();\n" +
                "  ActivityManager mActivityManager = (ActivityManager) context\n" +
                "    .getSystemService(Context.ACTIVITY_SERVICE);"
    }
}