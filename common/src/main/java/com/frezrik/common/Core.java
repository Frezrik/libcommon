/*
 * common
 *
 * Copyright (c) 2020  frezrik@126.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.frezrik.common;


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
