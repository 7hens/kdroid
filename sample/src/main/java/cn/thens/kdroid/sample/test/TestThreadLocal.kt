package cn.thens.kdroid.sample.test

import cn.thens.kdroid.util.Logdog


object TestThreadLocal {
    fun debug() {
        Thread {
            val threadLocal = ThreadLocal<String>()
            threadLocal.set("Thread Local")
            var in1 = ""
            var in2 = ""

            Thread {
                threadLocal.set("Thread One")
                in1 = threadLocal.get()
            }.start()

            Thread {
                threadLocal.set("Thread Two")
                in2 = threadLocal.get()
            }.start()

            Thread.sleep(1000)
            val out = threadLocal.get()
            Logdog.debug("out = $out\nin1 = $in1\nin2 = $in2")
        }.start()
    }
}