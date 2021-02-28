package com.github.enjektor.web.servlet;

import gnu.trove.map.TByteObjectMap;

import java.lang.reflect.Method;

public class ServletInitializerTuple {
    private TByteObjectMap<TByteObjectMap<Method>> methods;
    private String[][] endpoints;

    public ServletInitializerTuple(final TByteObjectMap<TByteObjectMap<Method>> methods,
                                   final String[][] endpoints) {
        this.methods = methods;
        this.endpoints = endpoints;
    }

    public TByteObjectMap<TByteObjectMap<Method>> getMethods() {
        return methods;
    }

    public void setMethods(TByteObjectMap<TByteObjectMap<Method>> methods) {
        this.methods = methods;
    }

    public String[][] getEndpoints() {
        return endpoints;
    }

    public void setEndpoints(String[][] endpoints) {
        this.endpoints = endpoints;
    }
}
