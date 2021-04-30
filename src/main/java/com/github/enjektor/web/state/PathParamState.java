package com.github.enjektor.web.state;

import java.lang.reflect.Method;
import java.util.regex.Pattern;

public class PathParamState {
    private final Object router;
    private final Method method;
    private final Pattern pattern;

    public static class Builder {
        private Object router;
        private Method method;
        private Pattern pattern;

        public Builder router(Object router) {
            this.router = router;
            return this;
        }

        public Builder method(Method method) {
            this.method = method;
            return this;
        }

        public Builder pattern(Pattern pattern) {
            this.pattern = pattern;
            return this;
        }

        public PathParamState build() {
            return new PathParamState(this);
        }

    }

    public PathParamState(Builder builder) {
        this.router = builder.router;
        this.method = builder.method;
        this.pattern = builder.pattern;
    }

    public Object getRouter() {
        return router;
    }

    public Method getMethod() {
        return method;
    }

    public Pattern getPattern() {
        return pattern;
    }
}
