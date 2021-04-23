package com.github.enjektor.web.state;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class RequestState {
    private final HttpServletRequest request;
    private final HttpServletResponse response;

    public static class Builder {
        
    }

    public RequestState(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }
}
