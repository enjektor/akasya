package com.github.enjektor.web.servlet.endpoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.enjektor.web.annotations.Body;
import com.github.enjektor.web.invocation.InvocationHandler;
import com.github.enjektor.web.invocation.InvocationHandlerImpl;
import com.github.enjektor.web.playground.domain.Human;
import com.github.enjektor.web.servlet.endpoint.hash.ByteHashProvider;
import com.github.enjektor.web.servlet.endpoint.hash.HashProvider;
import com.github.enjektor.web.servlet.endpoint.information.DefaultEndpointInformation;
import com.github.enjektor.web.servlet.endpoint.information.EndpointInformation;
import gnu.trove.map.TByteObjectMap;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import static com.github.enjektor.web.WebConstants.HTTP_METHOD_GET;
import static com.github.enjektor.web.WebConstants.HTTP_METHOD_POST;

public class DefaultEndpointManager implements EndpointManager {

    private final EndpointInformation<String> endpointInformation;
    private final HashProvider hashProvider;
    private final InvocationHandler invocationHandler;
    private final TByteObjectMap<Method>[] methods;
    private final Object routerObject;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public DefaultEndpointManager(final Object routerObject,
                                  final TByteObjectMap<Method>[] methods) {
        this.routerObject = routerObject;
        this.invocationHandler = InvocationHandlerImpl.getInstance();
        this.endpointInformation = DefaultEndpointInformation.getInstance();
        this.hashProvider = ByteHashProvider.getInstance();
        this.methods = methods;
    }

    @Override
    public void manageGet(HttpServletRequest req, HttpServletResponse res) {
        final String endpoint = endpointInformation.collectInformation(req);
        final byte unsignedHashValue = hashProvider.provide(endpoint);
        final Method methodThatWillExecute = methods[HTTP_METHOD_GET].get(unsignedHashValue);
        invocationHandler.invoke(routerObject, methodThatWillExecute, res);
    }

    @Override
    public void managePost(HttpServletRequest req, HttpServletResponse res) {
        final String endpoint = endpointInformation.collectInformation(req);
        final byte unsignedHashValue = hashProvider.provide(endpoint);
        final Method methodThatWillExecute = methods[HTTP_METHOD_POST].get(unsignedHashValue);
        invocationHandler.invoke(routerObject, methodThatWillExecute, req, res);
    }

    @Override
    public void manageDelete(HttpServletRequest req, HttpServletResponse res) {

    }

    @Override
    public void managePut(HttpServletRequest req, HttpServletResponse res) {

    }



}
