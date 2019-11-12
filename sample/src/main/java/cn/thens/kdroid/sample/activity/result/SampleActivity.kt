package cn.thens.kdroid.sample.activity.result

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import cn.thens.kdroid.core.app.ActivityRequest
import cn.thens.kdroid.core.util.Logdog

class SampleActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(createContentView(this))
    }

    private fun createContentView(context: Context): View {
        return LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            Button(context).apply {
                text = "startForResultWithCallback"
                setOnClickListener {
                    val intent = Intent(context, SecondActivity::class.java)
                    ActivityRequest.create(intent).run(context, object: ActivityRequest.Callback {
                        override fun onSuccess(code: Int, data: Intent) {
                            Logdog.debug("resultCode: $code")
                        }
                    })
                }
            }.also { addView(it) }
        }
    }

    class SecondActivity : Activity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(createContentView(this))
        }

        private fun createContentView(context: Context): View {
            return LinearLayout(context).apply {
                Button(context).apply {
                    text = "return"
                    setOnClickListener {
                        setResult(RESULT_OK)
                        finish()
                    }
                }.also { addView(it) }

                Button(context).apply {
                    text = "cancel"
                    setOnClickListener {
                        finish()
                    }
                }.also { addView(it) }
            }
        }
    }
}