package com.github.enjektor.web.invocation;

import com.github.enjektor.web.annotations.Delete;
import com.github.enjektor.web.annotations.Get;
import com.github.enjektor.web.annotations.Post;
import com.github.enjektor.web.annotations.Put;
import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

import java.lang.reflect.Method;

public class InvocationHandlersTargets {

    private final Class<?> routerClass;

    public InvocationHandlersTargets(Class<?> routerClass) {
        this.routerClass = routerClass;
    }

    public static TByteObjectMap<int[]> methodWeights() {
        TByteObjectMap<int[]> byteObjectMap = new TByteObjectHashMap<>();

        return null;
    }

    private String getInfo(Method method) {
        if (method.isAnnotationPresent(Get.class)) {
            return method.getAnnotation(Get.class).value();
        } else if (method.isAnnotationPresent(Post.class)) {
            return method.getAnnotation(Post.class).value();
        } else if (method.isAnnotationPresent(Put.class)) {
            return method.getAnnotation(Put.class).value();
        } else if (method.isAnnotationPresent(Delete.class)) {
            return method.getAnnotation(Delete.class).value();
        }
        return "";
    }

}
