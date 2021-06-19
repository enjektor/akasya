package com.github.enjektor.web.playground.middleware;

import com.github.enjektor.web.http.Context;
import com.github.enjektor.core.annotations.Dependency;

import java.io.IOException;

@Dependency
public interface Middleware {
    boolean next(Context context);
    void error(Context context) throws IOException;
}
