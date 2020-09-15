package cn.thens.kdroid.core.atomic

import org.junit.Test

class FuncGenerator {
    @Test
    fun generateInterfaces() {
        var text = ""
        for (i in 0..9) {
            text += """
                interface P$i<${genericParameterTypes(i)}R> : Func<R> {
                    operator fun invoke(${genericParameters(i)}): R
                }


            """.trimIndent()
        }
        println(text)
    }

    private fun genericParameterTypes(count: Int): String {
        return (1..count).joinToString("") { "P$it, " }
    }

    private fun genericParameters(count: Int): String {
        return (1..count).joinToString(", ") { "p$it: P$it" }
    }
}