package cn.thens.kdroid.core.codec.codec

import cn.thens.kdroid.core.io.Json
import cn.thens.kdroid.core.codec.testbean.Person
import com.google.gson.reflect.TypeToken
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import org.junit.Test

class JSONTest {
    @Test
    fun parse() {
        Json.parse<Person>(Person.MOCK_JSON)
                .let {
                    println("parse: $it")
                    assertTrue(it.equals(Person.instance))
                }
    }

    @Test
    fun parseType() {
        val jsonOne = Person.MOCK_JSON
        val json = "[$jsonOne, $jsonOne]"
        listOf(
                Json.parse<List<Person>>(json),
                Json.parse(json, Json.type(List::class, Person::class))
        )
                .forEach {
                    println("parse list: $it")
                    assertEquals(it.size, 2)
                    assertTrue(it[0].equals(Person.instance))
                }
    }

    @Test
    fun stringify() {
        Json.stringify(Person.instance)
                .let {
                    println("stringify: $it")
                    assertTrue(it.contains("firstName"))
                    assertTrue(it.contains("lastName"))
                    assertTrue(it.contains("12"))
                    assertTrue(it.contains("James"))
                    assertTrue(it.contains("Green"))
                    assertTrue(it.contains("age"))
                    assertTrue(it.contains(":"))
                }
    }

    /**
     * inline 函数在调用时会直接被替换成它的函数体
     * reified 表示会保留泛型的类型，必须和 inline 一起使用
     *
     * 也就是说会在调用这个 inline 函数的时候
     * 会直接将 Json.parse(json) 替换成 Json.parse(json, type<T>())，
     * 而 Json.type<T>() 也是个 inline 函数，被会替换成 object : TypeToken<Person>() {}.type
     */
    @Suppress("UNUSED_VALUE")
    inline fun <reified T> foo(json: String) {
        Json.parse<T>(json)
        Json.parse<T>(json, Json.type<T>())
        Json.parse<T>(json, object : TypeToken<T>() {}.type)

        var data: T

        // 原始代码
        data = Json.parse(json)

        // 编译后的代码，想象成 Json.parse(json) 字符串被编译器给替换了
        data = Json.parse(json, object : TypeToken<T>() {}.type)
    }
}