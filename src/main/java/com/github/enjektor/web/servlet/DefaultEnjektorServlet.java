package com.github.enjektor.web.servlet;

import com.github.enjektor.web.invocation.DefaultHttpInvocation;
import com.github.enjektor.web.invocation.HttpInvocation;
import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.lang.reflect.Method;

import static com.github.enjektor.web.constants.EnjektorWebConstants.HTTP_METHOD_GET;
import static com.github.enjektor.web.constants.EnjektorWebConstants.HTTP_METHOD_POST;
import static com.github.enjektor.web.constants.EnjektorWebConstants.HTTP_METHOD_DELETE;
import static com.github.enjektor.web.constants.EnjektorWebConstants.HTTP_METHOD_PUT;

public class DefaultEnjektorServlet extends HttpServlet {

    private final Object routerObject;
    private final Class<?> routerClass;
    private TByteObjectMap<TByteObjectMap<Method>> methods;
    private String[][] endpoints;

    public DefaultEnjektorServlet(final Object routerObject,
                                  final Class<?> routerClass) {
        this.routerObject = routerObject;
        this.routerClass = routerClass;
    }

    @Override
    public void init() throws ServletException {
        final HttpInvocation httpInvocation = new DefaultHttpInvocation();
        final ServletInitializerTuple servletInitializerTuple = httpInvocation.invoke(routerClass);
        methods = servletInitializerTuple.getMethods();
        endpoints = servletInitializerTuple.getEndpoints();
    }

    @Override
    protected void doGet(final HttpServletRequest req,
                         final HttpServletResponse resp) throws ServletException, IOException {
        final String servletPath = req.getServletPath();
        final String pathInfo = req.getPathInfo();
        final String fullPath = pathInfo != null ? servletPath + pathInfo : servletPath;
//        final Method methodThatWillExecute = methods.get(HTTP_METHOD_GET).
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doDelete(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPut(req, resp);
    }
}
