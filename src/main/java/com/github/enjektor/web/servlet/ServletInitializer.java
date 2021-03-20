package com.github.enjektor.web.servlet;

import gnu.trove.map.TByteObjectMap;

import java.lang.reflect.Method;

public interface ServletInitializer {
    TByteObjectMap<Method>[] initialize(Class<?> router);
}
