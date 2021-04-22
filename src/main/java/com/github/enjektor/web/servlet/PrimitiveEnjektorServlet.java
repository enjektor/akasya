package com.github.enjektor.web.servlet;

import com.github.enjektor.web.annotations.Get;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.lang.reflect.Method;

public class PrimitiveEnjektorServlet extends HttpServlet {

    private final Object routerObject;
    private final Class<?> routerClass;

    public PrimitiveEnjektorServlet(Object routerObject,
                                    Class<?> routerClass) {
        this.routerObject = routerObject;
        this.routerClass = routerClass;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        final Method[] declaredMethods = routerClass.getDeclaredMethods();

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPut(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doDelete(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
