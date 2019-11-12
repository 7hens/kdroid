/**
 * minify with babel https://babel.bootcss.com/repl/
 */
(function () {
  if (window.jBridge) return;

  (function () {
    var callbackMap = {};
    var handlerMap = {};

    function native(host, method, arg) {
      prompt('jbridge://' + host + '.jbridge.app/' + method + '/' + arg);
    }

    function __func(method, arg) {
      var handler = handlerMap[method];
      handler && handler(arg, function (data) {
        native('callback', method, arg);
      });
    }

    function __callback(method, data) {
      var callback = callbackMap[method];
      callback && callback(data);
    }

    function register(method, handler) {
      handlerMap[method] = handler;
    }

    function invoke(method, arg, callback) {
      if (callback) callbackMap[method] = callback;
      native('func', method, arg);
    }

    window.jBridge = {
      register: register,
      invoke: invoke,
      __func: __func,
      __callback: __callback
    };

    console.log("hello, jBridge");
  })();
})();
