package com.github.enjektor.akasya.playground.middleware;

import com.github.enjektor.akasya.http.Context;
import com.github.enjektor.core.annotations.Dependency;

import java.io.IOException;

@Dependency
public interface Middleware {
    boolean next(Context context);
    void error(Context context) throws IOException;
}
