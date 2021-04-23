package com.github.enjektor.web.state;

import java.util.regex.Pattern;

public class EndpointState {
    private final Pattern pattern;
    private final String endpoint;

    public static class Builder {
        private Pattern pattern;
        private String endpoint;

        public Builder pattern(Pattern pattern) {
            this.pattern = pattern;
            return this;
        }

        public Builder endpoint(String endpoint) {
            this.endpoint = endpoint;
            return this;
        }

        public EndpointState build() {
            return new EndpointState(this);
        }
    }

    public EndpointState(Builder builder) {
        this.pattern = builder.pattern;
        this.endpoint = builder.endpoint;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public String getEndpoint() {
        return endpoint;
    }
}
