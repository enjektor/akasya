package com.github.enjektor.akasya.servlet;

import com.github.enjektor.akasya.state.MethodState;

public interface ServletMethodInitializer {
    MethodState initializeGet(Class<?> routerClass);

    MethodState initializePost(Class<?> routerClass);

    MethodState initializeDelete(Class<?> routerClass);

    MethodState initializePut(Class<?> routerClass);
}
