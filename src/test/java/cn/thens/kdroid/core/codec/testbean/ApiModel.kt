package cn.thens.kdroid.core.codec.testbean

data class ApiModel<T>(
        val code: Int,
        val msg: String,
        val data: T
)