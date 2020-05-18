package cn.thens.kdroid.sample.vadapter

import cn.thens.kdroid.vadapter.VRecyclerAdapter

abstract class BaseSampleAdapter<E> : VRecyclerAdapter<E>() {
    abstract fun createDataList(): List<E>

    fun refill() {
        refill(createDataList())
    }
}