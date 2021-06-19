package com.github.enjektor.web.playground.middleware;

import com.github.enjektor.web.http.Context;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ServletMiddlewareWrapper implements Filter {

    private final Middleware middleware;

    public ServletMiddlewareWrapper(Middleware middleware) {
        this.middleware = middleware;
    }

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response, FilterChain chain) throws IOException, ServletException {
        Context context = new Context((HttpServletRequest) request, (HttpServletResponse) response);
        final boolean next = middleware.next(context);

        if (next) chain.doFilter(request, response);
        else middleware.error(context);
    }
}
