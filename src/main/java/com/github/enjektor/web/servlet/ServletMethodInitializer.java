package com.github.enjektor.web.servlet;

import com.github.enjektor.web.state.MethodState;

public interface ServletMethodInitializer {
    MethodState initializeGet(Class<?> routerClass);

    MethodState initializePost(Class<?> routerClass);

    MethodState initializeDelete(Class<?> routerClass);

    MethodState initializePut(Class<?> routerClass);
}
