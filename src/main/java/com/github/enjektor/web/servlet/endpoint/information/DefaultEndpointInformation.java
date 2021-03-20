package com.github.enjektor.web.servlet.endpoint.information;

import com.github.enjektor.web.policy.EndpointNamingPolicy;
import com.github.enjektor.web.policy.EndpointNamingPolicyImpl;
import jakarta.servlet.http.HttpServletRequest;

public class DefaultEndpointInformation implements EndpointInformation<String> {

    private static EndpointInformation<String> instance = null;
    private final EndpointNamingPolicy endpointNamingPolicy;

    private DefaultEndpointInformation() {
        this.endpointNamingPolicy = EndpointNamingPolicyImpl.getInstance();
    }

    @Override
    public String collectInformation(HttpServletRequest httpServletRequest) {
        final String servletPath = httpServletRequest.getServletPath();
        final String pathInfo = httpServletRequest.getPathInfo();
        final String path = pathInfo != null ? servletPath + pathInfo : servletPath;
        return endpointNamingPolicy.erase(path);
    }

    public static EndpointInformation<String> getInstance() {
        if (instance == null) instance = new DefaultEndpointInformation();
        return instance;
    }
}
