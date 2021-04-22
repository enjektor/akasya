package com.github.enjektor.web.state;

import gnu.trove.map.TByteObjectMap;

import java.lang.reflect.Method;
import java.util.List;

public class MethodState {
    private final TByteObjectMap<Method> methods;
    private final List<String> patterns;

    public static class Builder {
        private TByteObjectMap<Method> methods;
        private List<String> patterns;

        public Builder methods(TByteObjectMap<Method> methods) {
            this.methods = methods;
            return this;
        }

        public Builder patterns(List<String> patterns) {
            this.patterns = patterns;
            return this;
        }

        public MethodState build() {
            return new MethodState(this);
        }
    }

    private MethodState(Builder builder) {
        this.methods = builder.methods;
        this.patterns = builder.patterns;
    }

    public TByteObjectMap<Method> getMethods() {
        return methods;
    }

    public List<String> getPatterns() {
        return patterns;
    }
}
