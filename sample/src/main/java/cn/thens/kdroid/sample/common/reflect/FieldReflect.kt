package cn.thens.kdroid.sample.common.reflect

import java.lang.reflect.Field

class FieldReflect(private val obj: Any, private val field: Field) : Reflect {
    constructor(obj: Any, name: String) : this(obj, ReflectUtils.field(obj.javaClass, name))

    override fun get(): Any {
        return field.get(obj)
    }

    fun set(value: Any): Reflect {
        field.set(obj, value)
        return Reflect.on(obj)
    }

    fun name(): String {
        return field.name
    }

    fun type(): Class<*> {
        return field.type
    }
}