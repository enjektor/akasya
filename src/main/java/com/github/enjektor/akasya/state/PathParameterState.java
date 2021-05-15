package com.github.enjektor.akasya.state;

import java.util.regex.Pattern;

public class PathParameterState {
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

        public PathParameterState build() {
            return new PathParameterState(this);
        }
    }

    public PathParameterState(Builder builder) {
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
