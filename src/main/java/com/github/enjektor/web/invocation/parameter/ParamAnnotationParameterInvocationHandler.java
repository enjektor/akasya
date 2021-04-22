package com.github.enjektor.web.invocation.parameter;

import com.github.enjektor.web.annotations.Param;
import jakarta.servlet.http.HttpServletRequest;

import java.lang.reflect.Parameter;

public class ParamAnnotationParameterInvocationHandler implements ParameterInvocationHandler {

    @Override
    public Object handle(final HttpServletRequest httpServletRequest,
                         final Parameter parameter,
                         final String path) {
        final Param annotation = parameter.getAnnotation(Param.class);

        final String requestURI = httpServletRequest.getRequestURI();
        final String parameterName = !annotation.value().isEmpty() ? template(annotation.value()) : template(parameter.getName());
        final int startIndex = path.indexOf(parameterName);

        return requestURI.substring(startIndex).split("/")[0];
    }

    private String template(String any) {
        return "{" + any + "}";
    }
}
