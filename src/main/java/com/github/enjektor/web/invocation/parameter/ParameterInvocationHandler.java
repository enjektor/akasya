package com.github.enjektor.web.invocation.parameter;

import jakarta.servlet.http.HttpServletRequest;

import java.lang.reflect.Parameter;

public interface ParameterInvocationHandler {
    Object handle(HttpServletRequest httpServletRequest, Parameter parameter, String path);
}
