package cn.thens.kdroid.extra

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

internal object ExtraProperty {
    inline fun <R : Any, T> with(crossinline get: R.(String) -> T, crossinline set: R.(String, T) -> Unit) = run {
        object : ReadWriteProperty<R, T> {
            override fun getValue(thisRef: R, property: KProperty<*>): T {
                return get(thisRef, property.name)
            }

            override fun setValue(thisRef: R, property: KProperty<*>, value: T) {
                set(thisRef, property.name, value)
            }
        }
    }

    inline fun <R : Any, T> with(defaultValue: T, crossinline get: R.(String, T) -> T, crossinline set: R.(String, T) -> Unit) = run {
        object : ReadWriteProperty<R, T> {
            override fun getValue(thisRef: R, property: KProperty<*>): T {
                return get(thisRef, property.name, defaultValue)
            }

            override fun setValue(thisRef: R, property: KProperty<*>, value: T) {
                set(thisRef, property.name, value)
            }
        }
    }
}
