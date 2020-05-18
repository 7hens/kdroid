package cn.thens.kdroid.io

import android.content.SharedPreferences
import android.util.LruCache
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

interface Warehouse<K, V> {
    fun pick(key: K): Ware<V>

    fun cache(cache: Warehouse<K, V>): Warehouse<K, V> {
        val source = this
        return object : Warehouse<K, V> {
            override fun pick(key: K): Ware<V> {
                return source.pick(key).cache(cache.pick(key))
            }
        }
    }

    fun <U> codecValue(codec: Codec<V, U>): Warehouse<K, U> {
        val source = this
        return object : Warehouse<K, U> {
            override fun pick(key: K): Ware<U> {
                return source.pick(key).codec(codec)
            }
        }
    }

    fun <L> liftKey(func: (L) -> K): Warehouse<L, V> {
        val source = this
        return object : Warehouse<L, V> {
            override fun pick(key: L): Ware<V> {
                return source.pick(func.invoke(key))
            }
        }
    }

    companion object {

        fun file(): Warehouse<File, ByteArray> {
            return object : Warehouse<File, ByteArray> {
                override fun pick(key: File): Ware<ByteArray> {
                    return object : Ware<ByteArray> {
                        override suspend fun get(): ByteArray {
                            return withContext(Dispatchers.IO) {
                                FileUtils.readBytes(key)
                            }
                        }

                        override suspend fun put(value: ByteArray) {
                            withContext(Dispatchers.IO) {
                                FileUtils.writeBytes(key, value)
                            }
                        }
                    }
                }
            }
        }

        fun <K, V> from(cache: LruCache<K, V>): Warehouse<K, V> {
            return object : Warehouse<K, V> {
                override fun pick(key: K): Ware<V> {
                    return object : Ware<V> {
                        override suspend fun get(): V {
                            return cache.get(key) ?: throw Ware.NotFoundException()
                        }

                        override suspend fun put(value: V) {
                            cache.put(key, value)
                        }
                    }
                }
            }
        }

        fun from(prefs: SharedPreferences): Warehouse<String, String> {
            return object : Warehouse<String, String> {
                override fun pick(key: String): Ware<String> {
                    return object : Ware<String> {
                        override suspend fun get(): String {
                            return withContext(Dispatchers.IO) {
                                prefs.getString(key, null) ?: throw Ware.NotFoundException()
                            }
                        }

                        override suspend fun put(value: String) {
                            prefs.edit().putString(key, value).apply()
                        }
                    }
                }
            }
        }
    }
}
