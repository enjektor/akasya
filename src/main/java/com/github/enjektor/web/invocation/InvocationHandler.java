package com.github.enjektor.web.invocation;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.lang.reflect.Method;

public interface InvocationHandler {
    void invoke(Object routerObject, Method method, HttpServletRequest request, HttpServletResponse response);
}
