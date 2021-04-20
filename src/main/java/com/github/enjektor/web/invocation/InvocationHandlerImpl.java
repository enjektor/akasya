package com.github.enjektor.web.invocation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.enjektor.web.annotations.*;
import com.github.enjektor.web.invocation.parameter.BodyAnnotationParameterInvocationHandler;
import com.github.enjektor.web.invocation.parameter.ParamAnnotationParameterInvocationHandler;
import com.github.enjektor.web.invocation.parameter.ParameterInvocationHandler;
import com.github.enjektor.web.playground.domain.Human;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

public class InvocationHandlerImpl implements InvocationHandler {

    private static InvocationHandler instance;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Map<Class<? extends Annotation>, ParameterInvocationHandler> parameterInvocationHandlerMap = new HashMap<>();

    private InvocationHandlerImpl() {
        parameterInvocationHandlerMap.put(Body.class, new BodyAnnotationParameterInvocationHandler());
        parameterInvocationHandlerMap.put(Param.class, new ParamAnnotationParameterInvocationHandler());
    }

    @Override
    public void invoke(Object routerObject, Method method, HttpServletResponse response) {
        try {
            final String result = method.invoke(routerObject).toString();
            response.getOutputStream().write(result.getBytes());
        } catch (IllegalAccessException | InvocationTargetException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void invoke(Object routerObject,
                       Method method,
                       HttpServletRequest request,
                       HttpServletResponse response) {
        response.setHeader("Content-Type", "application/json");
        final Parameter[] parameters = method.getParameters();
        final String info = getInfo(method);
        final Object[] params = new Object[parameters.length];

        byte count = (byte) 0;
        for (Parameter parameter : method.getParameters()) {
            final Annotation annotation = parameter.getAnnotations()[0];
            final Object handle = parameterInvocationHandlerMap.get(annotation.getClass()).handle(request, parameter, info);
            params[count++] = handle;
        }

        try {
            final Object invoke = method.invoke(routerObject, params);
            final String s = objectMapper.writeValueAsString(invoke);
            response.getOutputStream().write(s.getBytes());
        } catch (IllegalAccessException | InvocationTargetException | IOException e) {
            e.printStackTrace();
        }
    }

    public static InvocationHandler getInstance() {
        if (instance == null) instance = new InvocationHandlerImpl();
        return instance;
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
