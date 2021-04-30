package com.github.enjektor.web.invocation.parameter;

import com.github.enjektor.web.annotations.Param;
import jakarta.servlet.http.HttpServletRequest;

import java.lang.reflect.Parameter;

public class ParamAnnotationParameterInvocationHandler implements ParameterInvocationHandler {

    @Override
    public Object handle(final HttpServletRequest httpServletRequest,
                         final Parameter parameter,
                         final String pathInMethod) {
        final Param annotation = parameter.getAnnotation(Param.class);

        final String pathInServlet = httpServletRequest.getRequestURI();
        final String parameterName = template(annotation.value());
        final int startIndex = pathInMethod.indexOf(parameterName);

        return pathInServlet.substring(startIndex).split("/")[0];
    }

    private String template(String any) {
        return "{" + any + "}";
    }
}
