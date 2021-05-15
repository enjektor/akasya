package com.github.enjektor.akasya.state;

import java.lang.reflect.Method;
import java.util.regex.Pattern;

public class EndpointState {
    private final Method method;
    private final Pattern pattern;
    private Object routerObject;

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

        public EndpointState build() {
            return new EndpointState(this);
        }

    }

    public EndpointState(Builder builder) {
        this.method = builder.method;
        this.pattern = builder.pattern;
    }

    public byte stateType() {
        return (byte) (pattern != null ? 1 : 0);
    }

    public Method getMethod() {
        return method;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public void setRouterObject(Object routerObject) {
        this.routerObject = routerObject;
    }

    public Object getRouterObject() {
        return routerObject;
    }
}
