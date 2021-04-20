package com.github.enjektor.web.invocation.parameter;

import com.github.enjektor.web.annotations.Param;
import jakarta.servlet.http.HttpServletRequest;

import java.lang.reflect.Parameter;
import java.util.Optional;

public class ParamAnnotationParameterInvocationHandler implements ParameterInvocationHandler {

    @Override
    public Object handle(final HttpServletRequest httpServletRequest,
                         final Parameter parameter,
                         final String path) {

        final String requestURI = httpServletRequest.getRequestURI();
        final Param annotation = parameter.getAnnotation(Param.class);
        final String pathParamName = !annotation.value().isEmpty() ? annotation.value() : parameter.getName();



        return null;
    }
}
