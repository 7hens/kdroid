package cn.thens.kdroid.io

import android.content.SharedPreferences
import android.util.LruCache
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

interface Store<K, V> {
    fun pick(key: K): Storage<V>

    fun cache(cache: Store<K, V>): Store<K, V> {
        val source = this
        return object : Store<K, V> {
            override fun pick(key: K): Storage<V> {
                return source.pick(key).cache(cache.pick(key))
            }
        }
    }

    fun <U> codecValue(codec: Codec<V, U>): Store<K, U> {
        val source = this
        return object : Store<K, U> {
            override fun pick(key: K): Storage<U> {
                return source.pick(key).codec(codec)
            }
        }
    }

    fun <L> liftKey(func: (L) -> K): Store<L, V> {
        val source = this
        return object : Store<L, V> {
            override fun pick(key: L): Storage<V> {
                return source.pick(func.invoke(key))
            }
        }
    }

    companion object {
        inline fun <K, V> create(crossinline fn: (key: K) -> Storage<V>): Store<K, V> {
            return object : Store<K, V> {
                override fun pick(key: K): Storage<V> {
                    return fn(key)
                }
            }
        }

        fun file(): Store<File, ByteArray> {
            return create { key ->
                object : Storage<ByteArray> {
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

        fun <K, V> from(cache: LruCache<K, V>): Store<K, V> {
            return create { key ->
                object : Storage<V> {
                    override suspend fun get(): V {
                        return cache.get(key) ?: throw Storage.NotFoundException()
                    }

                    override suspend fun put(value: V) {
                        cache.put(key, value)
                    }
                }
            }
        }

        fun from(prefs: SharedPreferences): Store<String, String> {
            return create { key ->
                object : Storage<String> {
                    override suspend fun get(): String {
                        return withContext(Dispatchers.IO) {
                            prefs.getString(key, null) ?: throw Storage.NotFoundException()
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
