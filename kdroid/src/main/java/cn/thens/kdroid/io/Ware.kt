package cn.thens.kdroid.io

import java.lang.ref.WeakReference

interface Ware<V> {
    suspend fun get(): V

    suspend fun put(value: V)

    suspend fun optional(): V? {
        return try {
            get()
        } catch (e: Throwable) {
            null
        }
    }

    fun cache(cache: Ware<V>): Ware<V> {
        val source = this
        return object : Ware<V> {
            override suspend fun get(): V {
                return try {
                    cache.get()
                } catch (e: Throwable) {
                    source.get().also { cache.put(it) }
                }
            }

            override suspend fun put(value: V) {
                cache.put(value)
                source.put(value)
            }
        }
    }

    fun <U> codec(codec: Codec<V, U>): Ware<U> {
        val source = this
        return object : Ware<U> {
            override suspend fun get(): U {
                return codec.encode(source.get())
            }

            override suspend fun put(value: U) {
                source.put(codec.decode(value))
            }
        }
    }

    class NotFoundException : RuntimeException()

    companion object {
        fun <V> memory(): Ware<V> {
            return object : Ware<V> {
                private var cache = WeakReference<V>(null)

                override suspend fun get(): V {
                    return cache.get() ?: throw NotFoundException()
                }

                override suspend fun put(value: V) {
                    cache = WeakReference(value)
                }
            }
        }
    }
}
