package com.github.enjektor.web.servlet;

import com.github.enjektor.web.annotations.*;
import com.github.enjektor.web.invocation.parameter.helper.ParamRegexHelper;
import com.github.enjektor.web.invocation.parameter.helper.PrimitiveParamRegexHelper;
import com.github.enjektor.web.utils.hash.ByteHashProvider;
import com.github.enjektor.web.utils.hash.HashProvider;
import com.github.enjektor.web.state.EndpointState;
import com.github.enjektor.web.state.MethodState;
import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class DefaultServletMethodInitializer implements ServletMethodInitializer {

    private static final ParamRegexHelper paramRegexHelper = new PrimitiveParamRegexHelper();
    private final HashProvider hashProvider;

    public DefaultServletMethodInitializer() {
        this.hashProvider = ByteHashProvider.getInstance();
    }

    @Override
    public MethodState initializeGet(Class<?> routerClass) {
        final TByteObjectMap<Method> map = new TByteObjectHashMap<>();
        final List<EndpointState> patterns = new ArrayList<>();
        final Method[] declaredMethods = routerClass.getDeclaredMethods();
        final String routerEndpoint = routerEndpoint(routerClass);

        for (Method method : declaredMethods) {
            if (method.isAnnotationPresent(Get.class)) {
                final Get getAnnotation = method.getAnnotation(Get.class);
                final String endpointValue = routerEndpoint + getAnnotation.value();

                final String s = Optional
                        .of(endpointValue)
                        .filter(val -> val.contains("{"))
                        .map(val -> {
                            final String regex = paramRegexHelper.regex(endpointValue);
                            final EndpointState endpointState = new EndpointState.Builder()
                                    .endpoint(regex)
                                    .pattern(Pattern.compile(regex))
                                    .build();

                            patterns.add(endpointState);
                            return regex;
                        }).orElse(endpointValue);

                final byte hashValue = hashProvider.provide(s);
                map.put(hashValue, method);
            }
        }

        return new MethodState.Builder()
                .methods(map)
                .states(patterns)
                .build();
    }

    @Override
    public MethodState initializePost(Class<?> routerClass) {
        return null;
    }

    @Override
    public MethodState initializeDelete(Class<?> routerClass) {
        return null;
    }

    @Override
    public MethodState initializePut(Class<?> routerClass) {
        return null;
    }
//
//    @Override
//    public TByteObjectMap<MethodState> initializePost(Class<?> routerClass) {
//        final TByteObjectMap<MethodState> map = new TByteObjectHashMap<>();
//        final Method[] declaredMethods = routerClass.getDeclaredMethods();
//        final String routerEndpoint = routerEndpoint(routerClass);
//
//        Arrays.stream(declaredMethods)
//                .filter(m -> m.isAnnotationPresent(Post.class))
//                .forEach(method -> {
//                    final Post postAnnotation = method.getAnnotation(Post.class);
//                    final String endpointValue = routerEndpoint + postAnnotation.value();
//
//                    final MethodStateInvocation methodStateInvocation = new PrimitiveMethodStateInvocation(endpointValue);
//                    final byte hashValue = hashProvider.provide(methodStateInvocation.hash());
//
//                    final MethodState methodState = new MethodState.Builder()
//                            .method(method)
//                            .pattern(methodStateInvocation.pattern())
//                            .build();
//
//                    map.put(hashValue, methodState);
//                });
//
//        return map;
//    }
//
//    @Override
//    public TByteObjectMap<MethodState> initializeDelete(Class<?> routerClass) {
//        final TByteObjectMap<MethodState> map = new TByteObjectHashMap<>();
//        final Method[] declaredMethods = routerClass.getDeclaredMethods();
//        final String routerEndpoint = routerEndpoint(routerClass);
//
//        Arrays.stream(declaredMethods)
//                .filter(m -> m.isAnnotationPresent(Delete.class))
//                .forEach(method -> {
//                    final Delete deleteAnnotation = method.getAnnotation(Delete.class);
//                    final String endpointValue = routerEndpoint + deleteAnnotation.value();
//
//                    final MethodStateInvocation methodStateInvocation = new PrimitiveMethodStateInvocation(endpointValue);
//                    final byte hashValue = hashProvider.provide(methodStateInvocation.hash());
//
//                    final MethodState methodState = new MethodState.Builder()
//                            .method(method)
//                            .pattern(methodStateInvocation.pattern())
//                            .build();
//
//                    map.put(hashValue, methodState);
//                });
//
//        return map;
//    }
//
//    @Override
//    public TByteObjectMap<MethodState> initializePut(Class<?> routerClass) {
//        final TByteObjectMap<MethodState> map = new TByteObjectHashMap<>();
//        final Method[] declaredMethods = routerClass.getDeclaredMethods();
//        final String routerEndpoint = routerEndpoint(routerClass);
//
//        Arrays.stream(declaredMethods)
//                .filter(m -> m.isAnnotationPresent(Put.class))
//                .forEach(method -> {
//                    final Put putAnnotation = method.getAnnotation(Put.class);
//                    final String endpointValue = routerEndpoint + putAnnotation.value();
//
//                    final MethodStateInvocation methodStateInvocation = new PrimitiveMethodStateInvocation(endpointValue);
//                    final byte hashValue = hashProvider.provide(methodStateInvocation.hash());
//
//                    final MethodState methodState = new MethodState.Builder()
//                            .method(method)
//                            .pattern(methodStateInvocation.pattern())
//                            .build();
//
//                    map.put(hashValue, methodState);
//                });
//
//        return map;
//    }

    private String routerEndpoint(Class<?> routerClass) {
        final Router router = routerClass.getAnnotation(Router.class);
        return router.value().intern();
    }
}
