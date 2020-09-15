package cn.thens.kdroid.core.codec.codec

import cn.thens.kdroid.io.StringCodec
import junit.framework.Assert.assertEquals
import org.junit.Test

open class StringCodecTest {
    @Test
    fun testHex() {
        val codec = StringCodec.hex()
        val raw = "你好"
        val hex = "e4bda0e5a5bd"
        assertEquals(hex, codec.encode(raw.toByteArray()))
        assertEquals(raw, String(codec.decode(hex)))
    }
}