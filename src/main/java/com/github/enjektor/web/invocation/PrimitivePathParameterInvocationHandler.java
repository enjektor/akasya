package com.github.enjektor.web.invocation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.enjektor.web.annotations.*;
import com.github.enjektor.web.state.PathParamState;
import com.github.enjektor.web.state.RequestState;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PrimitivePathParameterInvocationHandler implements PathParameterInvocationHandler {

    private static final byte INITIAL_CAPACITY = (byte) 3;
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static PathParameterInvocationHandler instance;

    @Override
    public void invoke(PathParamState pathParamState,
                       RequestState requestState) {
        final Method method = pathParamState.getMethod();
        final Pattern pattern = pathParamState.getPattern();
        final Object router = pathParamState.getRouter();

        final HttpServletRequest request = requestState.getRequest();
        final HttpServletResponse response = requestState.getResponse();

        final String requestURI = request.getRequestURI();

        response.setHeader("Content-Type", "application/json");
        Parameter[] parameters = method.getParameters();
        List<String> queries = new ArrayList<>(INITIAL_CAPACITY);

        byte pathParamCounter = (byte) 0;
        for (Parameter parameter : parameters) {
            if (parameter.isAnnotationPresent(Param.class)) {
                pathParamCounter++;
            } else if (parameter.isAnnotationPresent(Query.class)) {
                final Query annotation = parameter.getAnnotation(Query.class);
                queries.add(annotation.value());
            }
        }

        Object[] params = new Object[parameters.length];

        final Matcher matcher = pattern.matcher(requestURI);

        byte counter = (byte) 0;
        if (matcher.find()) {
            for (int i = 1; i < pathParamCounter + 1; i++) {
                params[i - 1] = matcher.group(i);
                counter++;
            }
        }

        byte requestQueryCounter = counter;
        for (String query : queries) params[requestQueryCounter++] = request.getParameter(query);

        try {
            final Object invoke = method.invoke(router, params);
            final String s = objectMapper.writeValueAsString(invoke);
            response.getOutputStream().write(s.getBytes());
        } catch (IllegalAccessException | InvocationTargetException | IOException e) {
            e.printStackTrace();
        }
    }

    public static PathParameterInvocationHandler getInstance() {
        if (instance == null) instance = new PrimitivePathParameterInvocationHandler();
        return instance;
    }

}
