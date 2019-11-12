package cn.thens.kdroid.sample.common.lazy;


import java.lang.reflect.Constructor;

import androidx.annotation.NonNull;

/**
 * 惰性值。支持并发，可用于单例。
 *
 * @author 7hens
 */
public class Lazy<T> {
    private final Func0<? extends T> creator;

    private Lazy(Func0<? extends T> creator) {
        this.creator = creator;
    }

    private Once<T> once = new Once<>();

    /**
     * 获取值。
     */
    public T get() {
        return once.invoke(creator);
    }

    /**
     * 使用类的无参构造函数作来创建惰性值。
     * 如果没有无参构造函数，则会报错。
     */
    public static <T> Lazy<T> create(@NonNull final Class<? extends T> clazz) {
        return create(() -> {
            try {
                Constructor<? extends T> constructor = clazz.getDeclaredConstructor();
                constructor.setAccessible(true);
                return constructor.newInstance();
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * 创建一个惰性值。
     *
     * @param creator 创建函数
     * @param <T>     类型
     * @return 惰性值
     */
    public static <T> Lazy<T> create(@NonNull final Func0<? extends T> creator) {
        return new Lazy<>(creator);
    }
}
