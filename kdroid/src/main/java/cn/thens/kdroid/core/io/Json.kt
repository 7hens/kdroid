package cn.thens.kdroid.core.io

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import java.io.Reader
import java.io.StringReader
import java.io.Writer
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import kotlin.reflect.KClass

open class Json {
    @Suppress("MemberVisibilityCanBePrivate", "SpellCheckingInspection")
    protected val gson: Gson by lazy { Gson() }

    fun <T> parse(reader: Reader, type: Type): T {
        return gson.fromJson(reader, type)
    }

    inline fun <reified T> parse(json: String): T {
        return parse(json, type<T>())
    }

    fun <T> parse(text: String, type: Type): T {
        return parse(StringReader(text), type)
    }

    fun <T> parse(json: JsonElement, type: Type): T {
        return gson.fromJson(json, type)
    }

    fun <T> stringify(any: T, writer: Writer) {
        if (any is JsonElement) return gson.toJson(any, writer)
        return gson.toJson(any, writer)
    }

    fun <T> stringify(any: T): String {
        if (any is JsonElement) return gson.toJson(any)
        return gson.toJson(any)
    }

    inline fun <reified T> toCodec(): Codec<String, T> {
        val type = type<T>()
        return object : Codec<String, T> {
            override fun encode(plain: String): T {
                return parse(plain, type)
            }

            override fun decode(cipher: T): String {
                return stringify(cipher)
            }
        }
    }

    companion object : Json() {
        inline fun <reified T> type(): Type {
            return object : TypeToken<T>() {}.type
        }

        fun type(rawType: Type, vararg typeArgs: Type): Type {
            return object : ParameterizedType {
                override fun getOwnerType(): Type? = null
                override fun getRawType(): Type = rawType
                override fun getActualTypeArguments(): Array<out Type> = typeArgs
            }
        }

        fun type(rawType: KClass<*>, vararg typeArgs: KClass<*>): Type {
            return type(rawType.java, *typeArgs.map { it.java }.toTypedArray())
        }
    }
}
