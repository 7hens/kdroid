package cn.thens.kdroid.sample.common.lazy;

/**
 * @author 7hens
 */
@SuppressWarnings("unused")
public interface FuncN<R> extends Func<R> {
    R invoke(Object... params);
}
