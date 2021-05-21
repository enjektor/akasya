package com.github.enjektor.akasya.http;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

public class Context {
    private final HttpServletRequest httpServletRequest;
    private final HttpServletResponse httpServletResponse;

    public Context(final HttpServletRequest httpServletRequest,
                   final HttpServletResponse httpServletResponse) {
        this.httpServletRequest = httpServletRequest;
        this.httpServletResponse = httpServletResponse;
    }

    public InputStream bodyAsInputStream() throws IOException {
        return httpServletRequest.getInputStream();
    }

    public String query(String name) {
        return httpServletRequest.getParameter(name);
    }

    public String query(String name, String defaultValue) {
        final String query = httpServletRequest.getParameter(name);
        return Optional.ofNullable(query).orElse(defaultValue);
    }

    public String header(String name) {
        return httpServletRequest.getHeader(name);
    }

    public String header(String name, String defaultValue) {
        final String header = httpServletRequest.getHeader(name);
        return Optional.ofNullable(header).orElse(defaultValue);
    }

    public void send(String message) throws IOException {
        final ServletOutputStream outputStream = httpServletResponse.getOutputStream();
        outputStream.write(message.getBytes());
    }

    public void status(int status) {
        httpServletResponse.setStatus(status);
    }
}
