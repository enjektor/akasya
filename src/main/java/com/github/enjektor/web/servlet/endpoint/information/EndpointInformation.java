package com.github.enjektor.web.servlet.endpoint.information;

import jakarta.servlet.http.HttpServletRequest;

public interface EndpointInformation<T> {
    T collectInformation(HttpServletRequest httpServletRequest);
}
