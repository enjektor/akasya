package com.github.enjektor.web.servlet.collector;

import jakarta.servlet.http.HttpServletRequest;

public interface RequestPathInformationCollector<T> {
    T collect(HttpServletRequest httpServletRequest);
}
