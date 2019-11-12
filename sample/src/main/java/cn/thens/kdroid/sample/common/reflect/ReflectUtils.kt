package cn.thens.kdroid.sample.common.reflect

import java.lang.reflect.Constructor
import java.lang.reflect.Field
import java.lang.reflect.Method
import java.lang.reflect.Modifier

object ReflectUtils {

    @JvmStatic
    fun get(cls: Class<*>, name: String): Any? {
        return field(cls, name).get(null)
    }

    @JvmStatic
    fun get(obj: Any, name: String): Any? {
        return field(obj.javaClass, name).get(obj)
    }

    @JvmStatic
    fun set(cls: Class<*>, name: String, value: Any?) {
        field(cls, name).set(null, value)
    }

    @JvmStatic
    fun set(obj: Any, name: String, value: Any?) {
        field(obj.javaClass, name).set(obj, value)
    }

    @JvmStatic
    fun invoke(cls: Class<*>, name: String, vararg args: Any?): Any? {
        return method(cls, name, *types(*args)).invoke(null, *args)
    }

    @JvmStatic
    fun invoke(obj: Any, name: String, vararg args: Any?): Any? {
        return method(obj.javaClass, name, *types(*args)).invoke(obj, *args)
    }

    @JvmStatic
    fun <T> newInstance(cls: Class<T>, vararg args: Any?): T {
        return constructor(cls, *types(*args)).newInstance(*args)
    }

    @JvmStatic
    @Throws(NoSuchFieldException::class)
    fun field(cls: Class<*>, name: String): Field {
        try {
            return cls.getField(name)
        } catch (e: NoSuchFieldException) {
            var type: Class<*>? = cls
            while (type != null) {
                try {
                    return type.getDeclaredField(name)
                } catch (e: NoSuchFieldException) {
                }
                type = type.superclass
            }
        }
        throw NoSuchFieldException(name)
    }

    @JvmStatic
    fun fields(cls: Class<*>): Map<String, Field> {
        val map = mutableMapOf<String, Field>()
        var type: Class<*>? = cls
        while (type != null) {
            type.declaredFields.forEach { declaredField ->
                if (!Modifier.isStatic(declaredField.modifiers)) {
                    val name = declaredField.name
                    if (!map.containsKey(name)) {
                        map[name] = declaredField
                    }
                }
            }
            type = type.superclass
        }
        return map
    }

    @JvmStatic
    @Throws(NoSuchMethodException::class)
    fun method(cls: Class<*>, name: String, vararg types: Class<*>): Method {
        try {
            return cls.getMethod(name, *types)
        } catch (e: NoSuchFieldException) {
            var type: Class<*>? = cls
            while (type != null) {
                try {
                    return type.getDeclaredMethod(name, *types)
                } catch (e: NoSuchMethodException) {
                    for (method in type.declaredMethods) {
                        if (method.name == name && matches(method.parameterTypes, types)) {
                            return method
                        }
                    }
                }
                type = type.superclass
            }
        }
        throw NoSuchMethodException(name)
    }

    @JvmStatic
    @Suppress("UNCHECKED_CAST")
    @Throws(NoSuchMethodException::class)
    fun <T> constructor(cls: Class<T>, vararg types: Class<*>): Constructor<T> {
        try {
            return cls.getDeclaredConstructor(*types)
        } catch (e: NoSuchMethodException) {
            for (constructor in cls.declaredConstructors) {
                if (matches(constructor.parameterTypes, types)) {
                    return constructor as Constructor<T>
                }
            }
            throw NoSuchMethodException()
        }
    }

    fun types(vararg values: Any?): Array<Class<*>> {
        return values.map { it?.javaClass ?: NULL::class.java }.toTypedArray()
    }

    @JvmStatic
    fun matches(declaredTypes: Array<Class<*>>, actualTypes: Array<out Class<*>>): Boolean {
        if (declaredTypes.size != actualTypes.size) return false
        for (i in actualTypes.indices) {
            if (actualTypes[i] == NULL::class.java)
                continue
            if (matches(declaredTypes[i], actualTypes[i]))
                continue
            return false
        }
        return true
    }

    @JvmStatic
    fun matches(declaredType: Class<*>, actualType: Class<*>): Boolean {
        return wrapper(declaredType).isAssignableFrom(wrapper(actualType))
    }

    private fun wrapper(type: Class<*>): Class<*> {
        if (type.isPrimitive) {
            when (type) {
                Boolean::class.javaPrimitiveType -> return Boolean::class.java
                Int::class.javaPrimitiveType -> return Int::class.java
                Long::class.javaPrimitiveType -> return Long::class.java
                Short::class.javaPrimitiveType -> return Short::class.java
                Byte::class.javaPrimitiveType -> return Byte::class.java
                Double::class.javaPrimitiveType -> return Double::class.java
                Float::class.javaPrimitiveType -> return Float::class.java
                Char::class.javaPrimitiveType -> return Char::class.java
                Void.TYPE -> return Void::class.java
            }
        }
        return type
    }

    private class NULL
}