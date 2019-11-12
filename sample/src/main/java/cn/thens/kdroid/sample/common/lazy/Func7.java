package cn.thens.kdroid.sample.common.lazy;

/**
 * @author 7hens
 */
public interface Func7<P1, P2, P3, P4, P5, P6, P7, R> extends Func<R> {
    R invoke(P1 p1, P2 p2, P3 p3, P4 p4, P5 p5, P6 p6, P7 p7);
}
