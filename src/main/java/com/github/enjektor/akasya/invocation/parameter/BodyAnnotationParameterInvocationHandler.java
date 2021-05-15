package com.github.enjektor.akasya.invocation.parameter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.enjektor.akasya.annotations.Body;
import jakarta.servlet.http.HttpServletRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Parameter;
import java.util.Optional;

public class BodyAnnotationParameterInvocationHandler implements ParameterInvocationHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Object handle(HttpServletRequest httpServletRequest,
                         Parameter parameter,
                         String path) {
        return Optional
                .of(parameter)
                .filter(param -> param.isAnnotationPresent(Body.class))
                .map(param -> {
                    final Class<?> type = param.getType();
                    return getBody(httpServletRequest, type);
                }).orElse(null);
    }

    private Object getBody(HttpServletRequest request, Class<?> type) {
        StringBuilder buffer = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            return objectMapper.readValue(buffer.toString(), type);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
