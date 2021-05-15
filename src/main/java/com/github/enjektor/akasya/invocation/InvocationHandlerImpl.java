package com.github.enjektor.akasya.invocation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.enjektor.akasya.annotations.Body;
import com.github.enjektor.akasya.annotations.Delete;
import com.github.enjektor.akasya.annotations.Get;
import com.github.enjektor.akasya.annotations.Param;
import com.github.enjektor.akasya.annotations.Post;
import com.github.enjektor.akasya.annotations.Put;
import com.github.enjektor.akasya.invocation.parameter.BodyAnnotationParameterInvocationHandler;
import com.github.enjektor.akasya.invocation.parameter.ParamAnnotationParameterInvocationHandler;
import com.github.enjektor.akasya.invocation.parameter.ParameterInvocationHandler;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

public class InvocationHandlerImpl implements InvocationHandler {

    private static final byte INITIAL_CAPACITY = (byte) 2;
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private final Map<Class<? extends Annotation>, ParameterInvocationHandler> parameterInvocationHandlerMap = new HashMap<>(INITIAL_CAPACITY);
    private static InvocationHandler instance;

    private InvocationHandlerImpl() {
        parameterInvocationHandlerMap.put(Body.class, new BodyAnnotationParameterInvocationHandler());
        parameterInvocationHandlerMap.put(Param.class, new ParamAnnotationParameterInvocationHandler());
    }

    @Override
    public void invoke(Object routerObject,
                       Method method,
                       HttpServletRequest request,
                       HttpServletResponse response) {
        response.setHeader("Content-Type", "application/json");
        Parameter[] parameters = method.getParameters();
        String info = request.getServletPath() + getInfo(method);
        Object[] params = new Object[parameters.length];

        byte count = (byte) 0;
        for (Parameter parameter : parameters) {
            final Class<? extends Annotation> annotationType = parameter.getAnnotations()[0].annotationType();
            final Object handle = parameterInvocationHandlerMap.get(annotationType).handle(request, parameter, info);
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
