package cn.thens.kdroid.sample.common.lazy;


import java.util.concurrent.atomic.AtomicBoolean;

import androidx.annotation.NonNull;

/**
 * @author 7hens
 */
public class Once<R> {
    private AtomicBoolean shouldEnter = new AtomicBoolean(true);
    private AtomicBoolean shouldLeave = new AtomicBoolean(false);
    private R result;

    public R invoke(@NonNull Func0<? extends R> func) {
        if (shouldEnter.compareAndSet(true, false)) {
            try {
                result = func.invoke();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                shouldLeave.set(true);
            }
        }
        //noinspection StatementWithEmptyBody
        while (!shouldLeave.get()) ;
        return result;
    }

    public static <R>
    Func0<R> wrap(@NonNull Func0<R> func) {
        final Once<R> once = new Once<>();
        return () -> once.invoke(func);
    }

    public static <P1, R>
    Func1<P1, R> wrap(@NonNull Func1<P1, R> func) {
        final Once<R> once = new Once<>();
        return p1 -> once.invoke(() -> func.invoke(p1));
    }

    public static <P1, P2, R>
    Func2<P1, P2, R> wrap(@NonNull Func2<P1, P2, R> func) {
        final Once<R> once = new Once<>();
        return (p1, p2) -> once.invoke(() -> func.invoke(p1, p2));
    }

    public static <P1, P2, P3, R>
    Func3<P1, P2, P3, R> wrap(@NonNull Func3<P1, P2, P3, R> func) {
        final Once<R> once = new Once<>();
        return (p1, p2, p3) -> once.invoke(() -> func.invoke(p1, p2, p3));
    }

    public static <P1, P2, P3, P4, R>
    Func4<P1, P2, P3, P4, R> wrap(@NonNull Func4<P1, P2, P3, P4, R> func) {
        final Once<R> once = new Once<>();
        return (p1, p2, p3, p4) -> once.invoke(() -> func.invoke(p1, p2, p3, p4));
    }

    public static <P1, P2, P3, P4, P5, R>
    Func5<P1, P2, P3, P4, P5, R> wrap(@NonNull Func5<P1, P2, P3, P4, P5, R> func) {
        final Once<R> once = new Once<>();
        return (p1, p2, p3, p4, p5) -> once.invoke(() -> func.invoke(p1, p2, p3, p4, p5));
    }

    public static <P1, P2, P3, P4, P5, P6, R>
    Func6<P1, P2, P3, P4, P5, P6, R> wrap(@NonNull Func6<P1, P2, P3, P4, P5, P6, R> func) {
        final Once<R> once = new Once<>();
        return (p1, p2, p3, p4, p5, p6) -> once.invoke(() -> func.invoke(p1, p2, p3, p4, p5, p6));
    }

    public static <P1, P2, P3, P4, P5, P6, P7, R>
    Func7<P1, P2, P3, P4, P5, P6, P7, R> wrap(@NonNull Func7<P1, P2, P3, P4, P5, P6, P7, R> func) {
        final Once<R> once = new Once<>();
        return (p1, p2, p3, p4, p5, p6, p7) -> once.invoke(() -> func.invoke(p1, p2, p3, p4, p5, p6, p7));
    }

    public static <P1, P2, P3, P4, P5, P6, P7, P8, R>
    Func8<P1, P2, P3, P4, P5, P6, P7, P8, R> wrap(@NonNull Func8<P1, P2, P3, P4, P5, P6, P7, P8, R> func) {
        final Once<R> once = new Once<>();
        return (p1, p2, p3, p4, p5, p6, p7, p8) -> once.invoke(() -> func.invoke(p1, p2, p3, p4, p5, p6, p7, p8));
    }

    public static <P1, P2, P3, P4, P5, P6, P7, P8, P9, R>
    Func9<P1, P2, P3, P4, P5, P6, P7, P8, P9, R> wrap(@NonNull Func9<P1, P2, P3, P4, P5, P6, P7, P8, P9, R> func) {
        final Once<R> once = new Once<>();
        return (p1, p2, p3, p4, p5, p6, p7, p8, p9) -> once.invoke(() -> func.invoke(p1, p2, p3, p4, p5, p6, p7, p8, p9));
    }

    public static <R>
    FuncN<R> wrap(@NonNull FuncN<R> func) {
        final Once<R> once = new Once<>();
        return (params) -> once.invoke(() -> func.invoke(params));
    }
}
