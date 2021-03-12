package com.github.enjektor.web.servlet;

import gnu.trove.map.TByteObjectMap;

import java.lang.reflect.Method;

public class ServletInitializerTuple {
    private TByteObjectMap<TByteObjectMap<Method>> methods;

    public ServletInitializerTuple(TByteObjectMap<TByteObjectMap<Method>> methods) {
        this.methods = methods;
    }

    public TByteObjectMap<TByteObjectMap<Method>> getMethods() {
        return methods;
    }

    public void setMethods(TByteObjectMap<TByteObjectMap<Method>> methods) {
        this.methods = methods;
    }
}
