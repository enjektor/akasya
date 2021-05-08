package com.github.enjektor.web.state;

public class HttpState {
    private final ParameterState parameterState;
    private final RequestState requestState;

    public static class Builder {
        private ParameterState parameterState;
        private RequestState requestState;

        public Builder parameterState(ParameterState pathParamState) {
            this.parameterState = pathParamState;
            return this;
        }

        public Builder requestState(RequestState requestState) {
            this.requestState = requestState;
            return this;
        }

        public HttpState build() {
            return new HttpState(this);
        }
    }

    public HttpState(Builder builder) {
        this.parameterState = builder.parameterState;
        this.requestState = builder.requestState;
    }

    public ParameterState getParameterState() {
        return parameterState;
    }

    public RequestState getRequestState() {
        return requestState;
    }
}
