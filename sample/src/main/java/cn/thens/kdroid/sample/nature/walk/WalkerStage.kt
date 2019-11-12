package cn.thens.kdroid.sample.nature.walk

import android.content.Context
import android.util.AttributeSet
import cn.thens.kdroid.sample.nature.base.Stage

class WalkerStage(context: Context, attrs: AttributeSet? = null) : Stage(context, attrs) {

    init {
        for (i in 0 until 16) {
            children.add(Walker(this))
        }
    }
}