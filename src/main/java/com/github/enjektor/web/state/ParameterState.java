package com.github.enjektor.web.state;

import java.lang.reflect.Method;
import java.util.regex.Pattern;

public class ParameterState {
    private final Method method;
    private final Pattern pattern;

    public static class Builder {
        private Method method;
        private Pattern pattern;

        public Builder method(Method method) {
            this.method = method;
            return this;
        }

        public Builder pattern(Pattern pattern) {
            this.pattern = pattern;
            return this;
        }

        public ParameterState build() {
            return new ParameterState(this);
        }

    }

    public ParameterState(Builder builder) {
        this.method = builder.method;
        this.pattern = builder.pattern;
    }

    public Method getMethod() {
        return method;
    }

    public Pattern getPattern() {
        return pattern;
    }
}
