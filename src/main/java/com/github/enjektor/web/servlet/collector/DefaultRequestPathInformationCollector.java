package com.github.enjektor.web.servlet.collector;

import com.github.enjektor.web.policy.EndpointNamingPolicy;
import com.github.enjektor.web.policy.EndpointNamingPolicyImpl;
import jakarta.servlet.http.HttpServletRequest;

public class DefaultRequestPathInformationCollector implements RequestPathInformationCollector<String> {

    private static RequestPathInformationCollector<String> instance = null;
    private final EndpointNamingPolicy endpointNamingPolicy;

    public DefaultRequestPathInformationCollector() {
        this.endpointNamingPolicy = EndpointNamingPolicyImpl.getInstance();
    }

    @Override
    public String collect(HttpServletRequest httpServletRequest) {
        final String servletPath = httpServletRequest.getServletPath();
        final String pathInfo = httpServletRequest.getPathInfo();
        final String path = pathInfo != null ? servletPath + pathInfo : servletPath;
        return endpointNamingPolicy.erase(path);
    }

    public static RequestPathInformationCollector<String> getInstance() {
        if (instance == null) instance = new DefaultRequestPathInformationCollector();
        return instance;
    }
}
