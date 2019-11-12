package cn.thens.kdroid.sample.common.lazy;

/**
 * @author 7hens
 */
public interface Func2<P1, P2, R> extends Func<R> {
    R invoke(P1 p1, P2 p2);
}
