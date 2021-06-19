package com.github.enjektor.web.playground.middleware;

import com.github.enjektor.core.annotations.Dependency;
import com.github.enjektor.core.annotations.Inject;
import com.github.enjektor.web.http.Context;
import com.github.enjektor.web.playground.repository.AuthenticationRepository;
import com.github.enjektor.web.playground.utils.Base64Utils;

import java.io.IOException;

@Dependency
public class BasicAuthenticationMiddleware implements Middleware {

    private static final int UNAUTHORIZED = 401;

    @Inject
    private AuthenticationRepository authenticationRepository;

    @Inject
    private Base64Utils base64Utils;

    @Override
    public boolean next(Context context) {
        final String authorization = context.header("Authorization");
        if (authorization == null) return false;

        final String decode = base64Utils.decode(authorization.substring(6));

        final String[] split = decode.split(":");
        final String username = split[0];
        final String password = split[1];

        return authenticationRepository.isAuthenticated(username, password);
    }

    @Override
    public void error(Context context) throws IOException {
        context.status(UNAUTHORIZED);
    }
}
