package com.github.enjektor.web.servlet;

import gnu.trove.map.TByteObjectMap;

import java.lang.reflect.Method;

public interface ServletMethodInitializer {
    TByteObjectMap<Method> initializeGet(Class<?> routerClass);
    TByteObjectMap<Method> initializePost(Class<?> routerClass);
    TByteObjectMap<Method> initializeDelete(Class<?> routerClass);
    TByteObjectMap<Method> initializePut(Class<?> routerClass);
}
