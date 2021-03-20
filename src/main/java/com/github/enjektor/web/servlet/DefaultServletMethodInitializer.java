package com.github.enjektor.web.servlet;

import com.github.enjektor.web.annotations.*;
import com.github.enjektor.web.servlet.endpoint.hash.ByteHashProvider;
import com.github.enjektor.web.servlet.endpoint.hash.HashProvider;
import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

import java.lang.reflect.Method;
import java.util.Arrays;

public class DefaultServletMethodInitializer implements ServletMethodInitializer {

    private final HashProvider hashProvider;

    public DefaultServletMethodInitializer() {
        this.hashProvider = ByteHashProvider.getInstance();
    }

    @Override
    public TByteObjectMap<Method> initializeGet(Class<?> routerClass) {
        final TByteObjectMap<Method> map = new TByteObjectHashMap<>();
        final Method[] declaredMethods = routerClass.getDeclaredMethods();
        final String routerEndpoint = routerEndpoint(routerClass);

        Arrays.stream(declaredMethods)
                .filter(m -> m.isAnnotationPresent(Get.class))
                .forEach(method -> {
                    final Get getAnnotation = method.getAnnotation(Get.class);
                    final String endpointValue = routerEndpoint + getAnnotation.value();
                    final byte hashValue = hashProvider.provide(endpointValue);
                    map.put(hashValue, method);
                });

        return map;
    }

    @Override
    public TByteObjectMap<Method> initializePost(Class<?> routerClass) {
        final TByteObjectMap<Method> map = new TByteObjectHashMap<>();
        final Method[] declaredMethods = routerClass.getDeclaredMethods();
        final String routerEndpoint = routerEndpoint(routerClass);

        Arrays.stream(declaredMethods)
                .filter(m -> m.isAnnotationPresent(Post.class))
                .forEach(method -> {
                    final Post postAnnotation = method.getAnnotation(Post.class);
                    final String endpointValue = routerEndpoint + postAnnotation.value();
                    final byte hashValue = hashProvider.provide(endpointValue);
                    map.put(hashValue, method);
                });

        return map;
    }

    @Override
    public TByteObjectMap<Method> initializeDelete(Class<?> routerClass) {
        final TByteObjectMap<Method> map = new TByteObjectHashMap<>();
        final Method[] declaredMethods = routerClass.getDeclaredMethods();
        final String routerEndpoint = routerEndpoint(routerClass);

        Arrays.stream(declaredMethods)
                .filter(m -> m.isAnnotationPresent(Delete.class))
                .forEach(method -> {
                    final Delete deleteAnnotation = method.getAnnotation(Delete.class);
                    final String endpointValue = routerEndpoint + deleteAnnotation.value();
                    final byte hashValue = hashProvider.provide(endpointValue);
                    map.put(hashValue, method);
                });

        return map;
    }

    @Override
    public TByteObjectMap<Method> initializePut(Class<?> routerClass) {
        final TByteObjectMap<Method> map = new TByteObjectHashMap<>();
        final Method[] declaredMethods = routerClass.getDeclaredMethods();
        final String routerEndpoint = routerEndpoint(routerClass);

        Arrays.stream(declaredMethods)
                .filter(m -> m.isAnnotationPresent(Put.class))
                .forEach(method -> {
                    final Put putAnnotation = method.getAnnotation(Put.class);
                    final String endpointValue = routerEndpoint + putAnnotation.value();
                    final byte hashValue = hashProvider.provide(endpointValue);
                    map.put(hashValue, method);
                });

        return map;
    }

    private String routerEndpoint(Class<?> routerClass) {
        final Router router = routerClass.getAnnotation(Router.class);
        return router.value().intern();
    }
}
