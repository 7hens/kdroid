package cn.thens.kdroid.core.storage

import android.content.Context
import android.content.SharedPreferences
import junit.framework.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class PreferencesStorageTest {
    lateinit var prefs: SharedPreferences

    @Before
    fun setup() {
        val context = RuntimeEnvironment.application
        prefs = context.getSharedPreferences("shared_prefs", Context.MODE_PRIVATE)
    }

    @Test
    fun set() {
        val node = Store.from(prefs, "set")
        val value = "hello,world"
        node.put(value)
        println(node.get())
        assertTrue(node.exists())
        assertEquals(node.get(), value)
        node.clear()
        assertTrue(!node.exists())
    }

    @Test(expected = Exception::class)
    fun get() {
        val node = Store.from(prefs, "get")
        node.get()
    }

    @Test
    fun contains() {
        val node = Store.from(prefs, "contains")
        assertFalse(node.exists())
    }
}
