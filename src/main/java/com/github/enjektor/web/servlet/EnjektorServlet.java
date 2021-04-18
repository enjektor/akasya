package com.github.enjektor.web.servlet;

import com.github.enjektor.web.servlet.endpoint.DefaultEndpointManager;
import com.github.enjektor.web.servlet.endpoint.EndpointManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class EnjektorServlet extends HttpServlet {

    private static final ServletInitializer servletInitializer = new DefaultServletInitializer();
    private final Object routerObject;
    private final Class<?> routerClass;
    private final EndpointManager endpointManager;

    public EnjektorServlet(final Object routerObject,
                           final Class<?> routerClass) {
        this.routerObject = routerObject;
        this.routerClass = routerClass;
        this.endpointManager = new DefaultEndpointManager(routerObject, servletInitializer.initialize(routerClass));
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
