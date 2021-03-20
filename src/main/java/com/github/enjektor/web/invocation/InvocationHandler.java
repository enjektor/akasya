package com.github.enjektor.web.invocation;

import jakarta.servlet.http.HttpServletResponse;

import java.lang.reflect.Method;

public interface InvocationHandler {
    void invoke(Method method, HttpServletResponse response);
}
