package cn.thens.kdroid.sample.common.lazy;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.Window;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

/**
 * @author 7hens
 */
public final class LazyView {

    private final Func1<Integer, View> finder;

    private LazyView(Func1<Integer, View> finder) {
        this.finder = finder;
    }

    @SuppressWarnings("unchecked")
    public <T extends View> Lazy<T> bind(final int id) {
        return Lazy.create(() -> (T) finder.invoke(id));
    }

    public static LazyView create(@NonNull Activity activity) {
        return new LazyView(activity::findViewById);
    }

    public static LazyView create(@NonNull View view) {
        return new LazyView(view::findViewById);
    }

    public static LazyView create(@NonNull Fragment fragment) {
        return new LazyView(id -> Objects.requireNonNull(fragment.getView()).findViewById(id));
    }

    public static LazyView create(@NonNull Dialog dialog) {
        return new LazyView(dialog::findViewById);
    }

    public static LazyView create(@NonNull Window window) {
        return new LazyView(window::findViewById);
    }
}
