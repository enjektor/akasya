package com.github.enjektor.web.state;

public class HttpState {
    private final EndpointState endpointState;

    public static class Builder {
        private EndpointState parameterState;

        public Builder parameterState(EndpointState pathParamState) {
            this.parameterState = pathParamState;
            return this;
        }
        public HttpState build() {
            return new HttpState(this);
        }
    }

    public HttpState(Builder builder) {
        this.endpointState = builder.parameterState;
    }

    public EndpointState getEndpointState() {
        return endpointState;
    }
}
