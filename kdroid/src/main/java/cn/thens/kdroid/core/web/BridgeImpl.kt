package cn.thens.kdroid.core.web

internal class BridgeImpl(val foreignInterface: Interface) : Bridge {
    private val handlerMap = HashMap<String, Bridge.Handler>()
    private val callbackMap = HashMap<String, Bridge.Callback>()

    val nativeInterface = object : Interface {
        override fun func(name: String, arg: String) {
            handlerMap[name]?.handle(arg) { data ->
                foreignInterface.callback(name, data)
            }
        }

        override fun callback(name: String, arg: String) {
            callbackMap[name]?.invoke(arg)
        }
    }

    override fun register(func: String, handler: Bridge.Handler) {
        handlerMap[func] = handler
    }

    override fun unregister(func: String) {
        handlerMap.remove(func)
    }

    override fun invoke(func: String, arg: String, callback: Bridge.Callback) {
        callbackMap[func] = callback
        foreignInterface.func(func, arg)
    }

    interface Interface {
        fun func(name: String, arg: String)
        fun callback(name: String, arg: String)
    }
}