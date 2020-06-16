package com.frezrik.common.internal;

import android.util.LruCache;

import androidx.annotation.NonNull;

public class ApiManager<T> {
    private LruCache<Class<T>, T> serviceLruCache;

    private static ApiManager instance;

    private ApiManager() {
        serviceLruCache = new LruCache<>(50);
    }

    public static ApiManager getInstance() {
        if (null == instance) {
            synchronized (ApiManager.class) {
                if (null == instance) {
                    instance = new ApiManager();
                }
            }
        }

        return instance;
    }

    public T service(@NonNull Class<T> serviceClass) {
        return serviceLruCache.get(serviceClass);
    }

    public void registerService(@NonNull Class<T> serviceClass, @NonNull T implApi) {
        serviceLruCache.put(serviceClass, implApi);
    }

    /*public  void unregisterService(@NonNull Class<T> serviceClass) {
        serviceLruCache.remove(serviceClass);
    }*/
}
