package com.github.enjektor.web.invocation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.enjektor.web.annotations.Body;
import com.github.enjektor.web.playground.domain.Human;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class InvocationHandlerImpl implements InvocationHandler {

    private static InvocationHandler instance;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private InvocationHandlerImpl() {

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
        try {
            final Parameter[] parameters = method.getParameters();
            final Parameter firstParameter = parameters[0];
            if (firstParameter.isAnnotationPresent(Body.class)) {
                try {
                    response.setHeader("Content-Type", "application/json");
                    final Class<?> type = firstParameter.getType();
                    final Object o = objectMapper.readValue(getBody(request), type);
                    final Object result = method.invoke(routerObject, o);
                    final String s = objectMapper.writeValueAsString(result);
                    response.getOutputStream().write(s.getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public static InvocationHandler getInstance() {
        if (instance == null) instance = new InvocationHandlerImpl();
        return instance;
    }

    public String getBody(HttpServletRequest request) throws IOException {
        StringBuilder buffer = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }
        return buffer.toString();
    }
}
