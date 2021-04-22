package com.github.enjektor.web.servlet;

import com.github.enjektor.web.state.MethodState;
import gnu.trove.map.TByteObjectMap;

import static com.github.enjektor.web.WebConstants.HTTP_METHOD_DELETE;
import static com.github.enjektor.web.WebConstants.HTTP_METHOD_GET;
import static com.github.enjektor.web.WebConstants.HTTP_METHOD_POST;
import static com.github.enjektor.web.WebConstants.HTTP_METHOD_PUT;

public class DefaultServletInitializer implements ServletInitializer {

    private final ServletMethodInitializer servletMethodInitializer;

    public DefaultServletInitializer() {
        this.servletMethodInitializer = new DefaultServletMethodInitializer();
    }

    @Override
    public TByteObjectMap<MethodState>[] initialize(Class<?> router) {
        final TByteObjectMap<MethodState>[] methodArray = new TByteObjectMap[4];
        methodArray[HTTP_METHOD_GET] = servletMethodInitializer.initializeGet(router);
        methodArray[HTTP_METHOD_POST] = servletMethodInitializer.initializePost(router);
        methodArray[HTTP_METHOD_DELETE] = servletMethodInitializer.initializeDelete(router);
        methodArray[HTTP_METHOD_PUT] = servletMethodInitializer.initializePut(router);

        return methodArray;
    }

}
