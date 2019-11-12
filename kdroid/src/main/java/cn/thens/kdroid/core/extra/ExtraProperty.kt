package cn.thens.kdroid.core.extra

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

internal object ExtraProperty {
    fun <R : Any, T> with(get: R.(String) -> T, set: R.(String, T) -> Unit) = run {
        object : ReadWriteProperty<R, T> {
            override fun getValue(thisRef: R, property: KProperty<*>): T {
                return get(thisRef, property.name)
            }

            override fun setValue(thisRef: R, property: KProperty<*>, value: T) {
                set(thisRef, property.name, value)
            }
        }
    }

    fun <R : Any, T> with(defaultValue: T, get: R.(String, T) -> T, set: R.(String, T) -> Unit) = run {
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
