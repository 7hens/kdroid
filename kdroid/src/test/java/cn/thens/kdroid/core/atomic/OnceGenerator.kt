package cn.thens.kdroid.core.atomic

import org.junit.Test

class OnceGenerator {
    @Test
    fun generateFunctions() {
        var text = ""
        for (i in 0..9) {
            val params = genericParameterTypes(i).toLowerCase()
            text += """
                fun <${genericParameterTypes(i)}, R>
                wrap(func: (${genericParameterTypes(i)})->R): (${genericParameterTypes(i)})->R {
                    val once = create<R>()
                    return { $params -> once.invoke { func($params) } }
                }


            """.trimIndent()
        }
        println(text)
    }

    private fun genericParameterTypes(count: Int): String {
        return (1..count).joinToString(", ") { "P$it" }
    }

    private fun genericParameters(count: Int): String {
        return (1..count).joinToString(", ") { "p$it: P$it" }
    }
}