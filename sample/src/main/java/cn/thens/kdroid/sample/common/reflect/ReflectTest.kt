package cn.thens.kdroid.sample.common.reflect

object ReflectTest {
    fun reflect() {
        Reflect.on("").field("length").get()
        Reflect.on("").field("length").set(1)
        Reflect.on("").call("substring", 1).field("length").get()
        Reflect.on("").fields["length"]!!.name()
    }

    fun plain() {
        val cls = String::class.java
        val field = cls.getField("length")
        field.get("")
        field.set(null, null)
    }
}