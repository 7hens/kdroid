package cn.thens.kdroid.sample.common.lazy;

/**
 * @author 7hens
 */
public interface Func1<P1, R> extends Func<R> {
    R invoke(P1 p1);
}
