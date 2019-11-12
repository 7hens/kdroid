package cn.thens.kdroid.sample.common.reflect

interface Reflect {
    fun get(): Any

    operator fun get(name: String): Any {
        return field(name).get()
    }

    operator fun set(name: String, value: Any) {
        field(name).set(value)
    }

    fun field(name: String): FieldReflect {
        return FieldReflect(get(), name)
    }

    val fields: Map<String, FieldReflect>
        get() {
            return ReflectUtils.fields(get().javaClass).mapValues { FieldReflect(get(), it.value) }
        }

    fun call(name: String, vararg args: Any): Reflect {
        val method = ReflectUtils.method(get().javaClass, name, *ReflectUtils.types(args))
        return on(method.invoke(get(), args))
    }

    companion object {
        fun on(obj: Any): Reflect {
            return object : Reflect {
                override fun get(): Any {
                    return obj
                }
            }
        }
    }
}