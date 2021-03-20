package com.github.enjektor.web.invocation;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class InvocationHandlerImpl implements InvocationHandler {

    private static InvocationHandler instance;

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

    public static InvocationHandler getInstance() {
        if (instance == null) instance = new InvocationHandlerImpl();
        return instance;
    }
}
