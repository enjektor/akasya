package com.github.enjektor.web.invocation;

import com.github.enjektor.web.servlet.ServletInitializerTuple;

public interface HttpInvocation {
    ServletInitializerTuple invoke(Class<?> routerClass);
}
