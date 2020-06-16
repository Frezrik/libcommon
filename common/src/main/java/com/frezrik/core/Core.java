package com.frezrik.core;


import androidx.annotation.NonNull;
import androidx.collection.LruCache;
import java.lang.reflect.Constructor;

public class Core {
    private static final LruCache serviceLruCache = new LruCache(50);

    public static void bind(Object obj) {
        String classFullName = obj.getClass().getName() + "$$BindApi";
        try {
            Class proxy = Class.forName(classFullName);
            Constructor constructor = proxy.getConstructor(obj.getClass());
            constructor.newInstance(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static <T> T service(@NonNull Class<T> serviceClass) {
        return (T) serviceLruCache.get(serviceClass);
    }

    public static <T> void registerService(@NonNull Class<T> serviceClass) {
        serviceLruCache.put(serviceClass, "");
    }

    public static <T> void unRegisterService(@NonNull Class<T> serviceClass) {
        serviceLruCache.remove(serviceClass);
    }
}
