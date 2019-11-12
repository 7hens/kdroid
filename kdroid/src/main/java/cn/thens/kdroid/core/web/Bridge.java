package cn.thens.kdroid.core.web;

import androidx.annotation.NonNull;

public interface Bridge {
    void register(@NonNull String func, @NonNull Handler handler);

    void unregister(@NonNull String func);

    void invoke(@NonNull String func, @NonNull String arg, @NonNull Callback callback);

    interface Callback {
        void invoke(@NonNull String arg);
    }

    interface Handler {
        void handle(@NonNull String arg, @NonNull Callback callback);
    }
}
