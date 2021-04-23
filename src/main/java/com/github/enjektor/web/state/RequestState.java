package com.github.enjektor.web.state;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class RequestState {
    private final HttpServletRequest request;
    private final HttpServletResponse response;

    public static class Builder {
        private HttpServletRequest request;
        private HttpServletResponse response;

        public Builder request(HttpServletRequest request) {
            this.request = request;
            return this;
        }

        public Builder response(HttpServletResponse response) {
            this.response = response;
            return this;
        }

        public RequestState build() {
            return new RequestState(this);
        }
    }

    public RequestState(Builder builder) {
        this.request = builder.request;
        this.response = builder.response;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }
}
