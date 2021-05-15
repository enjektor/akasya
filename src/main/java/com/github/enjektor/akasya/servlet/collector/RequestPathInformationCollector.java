package com.github.enjektor.akasya.servlet.collector;

import jakarta.servlet.http.HttpServletRequest;

public interface RequestPathInformationCollector<T> {
    T collect(HttpServletRequest httpServletRequest);
}
