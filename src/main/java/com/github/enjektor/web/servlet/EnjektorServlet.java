package com.github.enjektor.web.servlet;

import com.github.enjektor.web.servlet.endpoint.DefaultEndpointManager;
import com.github.enjektor.web.servlet.endpoint.EndpointManager;
import gnu.trove.map.TByteObjectMap;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.lang.reflect.Method;

public class EnjektorServlet extends HttpServlet {

    private final Object routerObject;
    private final Class<?> routerClass;
    private EndpointManager endpointManager;

    public EnjektorServlet(final Object routerObject,
                           final Class<?> routerClass) {
        this.routerObject = routerObject;
        this.routerClass = routerClass;
    }

    @Override
    public void init() throws ServletException {
        final ServletInitializer servletInitializer = new DefaultServletInitializer();
        final TByteObjectMap<Method>[] methods = servletInitializer.initialize(routerClass);
        endpointManager = new DefaultEndpointManager(routerObject, methods);
    }

    @Override
    protected void doGet(final HttpServletRequest req,
                         final HttpServletResponse resp) throws ServletException, IOException {
        endpointManager.manageGet(req, resp);
    }

    @Override
    protected void doPost(final HttpServletRequest req,
                          final HttpServletResponse resp) throws ServletException, IOException {
        endpointManager.managePost(req, resp);
    }

    @Override
    protected void doDelete(final HttpServletRequest req,
                            final HttpServletResponse resp) throws ServletException, IOException {
        endpointManager.manageDelete(req, resp);
    }

    @Override
    protected void doPut(final HttpServletRequest req,
                         final HttpServletResponse resp) throws ServletException, IOException {
        endpointManager.managePut(req, resp);
    }

}
