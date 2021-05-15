package com.github.enjektor.web.servlet.filter;

import jakarta.servlet.*;

import java.io.IOException;

public class SimpleServletFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        System.out.println("in filter");
        chain.doFilter(request, response);
    }
}
