package com.github.enjektor.akasya.playground.middleware;

import com.github.enjektor.akasya.http.Context;
import com.github.enjektor.akasya.playground.injectable.StringRandomizer;
import com.github.enjektor.core.annotations.Dependency;
import com.github.enjektor.core.annotations.Inject;

import java.io.IOException;

@Dependency
public class BasicAuthenticationMiddleware implements Middleware {

    @Inject
    private StringRandomizer stringRandomizer;

    @Override
    public boolean next(Context context) {
        return context.header("Authorization") != null;
    }

    @Override
    public void error(Context context) throws IOException {
        context.send(stringRandomizer.randomize());
        context.status(401);
    }
}
