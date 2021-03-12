package com.github.enjektor.web.servlet;

import com.github.enjektor.web.constants.EnjektorWebConstants;
import com.github.enjektor.web.invocation.DefaultHttpInvocation;
import com.github.enjektor.web.invocation.HttpInvocation;
import com.github.enjektor.web.policy.EndpointNamingPolicy;
import com.github.enjektor.web.policy.EndpointNamingPolicyImpl;
import gnu.trove.map.TByteObjectMap;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class DefaultEnjektorServlet extends HttpServlet implements EnjektorWebConstants {

    private static final byte HASH_KEY = 32;
    private final Object routerObject;
    private final Class<?> routerClass;
    private TByteObjectMap<TByteObjectMap<Method>> methods;
    private TByteObjectMap<Method> getMethods;
    private TByteObjectMap<Method> postMethods;
    private TByteObjectMap<Method> deleteMethods;
    private TByteObjectMap<Method> putMethods;
    private EndpointNamingPolicy endpointNamingPolicy;

    public DefaultEnjektorServlet(final Object routerObject,
                                  final Class<?> routerClass) {
        this.routerObject = routerObject;
        this.routerClass = routerClass;
        this.endpointNamingPolicy = EndpointNamingPolicyImpl.getInstance();
    }

    @Override
    public void init() throws ServletException {
        final HttpInvocation httpInvocation = new DefaultHttpInvocation();
        final ServletInitializerTuple servletInitializerTuple = httpInvocation.invoke(routerClass);
        methods = servletInitializerTuple.getMethods();
        getMethods = methods.get(HTTP_METHOD_GET);
        postMethods = methods.get(HTTP_METHOD_POST);
        deleteMethods = methods.get(HTTP_METHOD_DELETE);
        putMethods = methods.get(HTTP_METHOD_PUT);
    }

    @Override
    protected void doGet(final HttpServletRequest req,
                         final HttpServletResponse resp) throws ServletException, IOException {
        final String servletPath = req.getServletPath();
        final String pathInfo = req.getPathInfo();
        final String fullPath = pathInfo != null ? servletPath + pathInfo : servletPath;
        final String committedPath = endpointNamingPolicy.erase(fullPath);
        final byte hashValue = unsignedHashValue(committedPath);
        final Method methodThatWillExecute = getMethods.get(hashValue);
        try {
            final Object invoke = methodThatWillExecute.invoke(routerObject);
            resp.getOutputStream().write(invoke.toString().getBytes());
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
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

    private byte unsignedHashValue(String endpoint) {
        return (byte) ((endpoint.hashCode() % HASH_KEY) & MASKING_VALUE);
    }
}
