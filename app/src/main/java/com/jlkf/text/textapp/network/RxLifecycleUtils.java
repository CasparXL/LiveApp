package com.jlkf.text.textapp.network;





import android.arch.lifecycle.LifecycleOwner;

import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.AutoDisposeConverter;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;


public class RxLifecycleUtils {
    private RxLifecycleUtils() {
        throw new IllegalStateException("Can't instance the RxLifecycleUtils");
    }

    public static <T> AutoDisposeConverter<T> bindLifecycle(LifecycleOwner lifecycleOwner) {
        return AutoDispose.<T>autoDisposable(AndroidLifecycleScopeProvider
                .from(lifecycleOwner));
    }
}