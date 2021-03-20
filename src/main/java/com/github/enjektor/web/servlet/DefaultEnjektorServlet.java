package com.github.enjektor.web.servlet;

import com.github.enjektor.web.invocation.http.DefaultHttpInvocation;
import com.github.enjektor.web.invocation.http.HttpInvocation;
import com.github.enjektor.web.policy.EndpointNamingPolicy;
import com.github.enjektor.web.policy.EndpointNamingPolicyImpl;
import gnu.trove.map.TByteObjectMap;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.lang.reflect.Method;

public class DefaultEnjektorServlet extends HttpServlet {

    private final Object routerObject;
    private final Class<?> routerClass;
    private EndpointNamingPolicy endpointNamingPolicy;
    private TByteObjectMap<Method>[] methods;

    public DefaultEnjektorServlet(final Object routerObject,
                                  final Class<?> routerClass) {
        this.routerObject = routerObject;
        this.routerClass = routerClass;
        this.endpointNamingPolicy = EndpointNamingPolicyImpl.getInstance();
    }

    @Override
    public void init() throws ServletException {
        final HttpInvocation httpInvocation = new DefaultHttpInvocation();
        methods = httpInvocation.invoke(routerClass);
    }

    @Override
    protected void doGet(final HttpServletRequest req,
                         final HttpServletResponse resp) throws ServletException, IOException {
        final String servletPath = req.getServletPath();
        final String pathInfo = req.getPathInfo();
        final String fullPath = pathInfo != null ? servletPath + pathInfo : servletPath;
        final String committedPath = endpointNamingPolicy.erase(fullPath);
//        final byte hashValue = unsignedHashValue(committedPath);
//        final Method methodThatWillExecute = methods[HTTP_METHOD_GET].get(hashValue);
//        try {
//            final Object invoke = methodThatWillExecute.invoke(routerObject);
//            resp.getOutputStream().write(invoke.toString().getBytes());
//        } catch (IllegalAccessException | InvocationTargetException e) {
//            e.printStackTrace();
//        }
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
