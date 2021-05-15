package com.github.enjektor.web.servlet.manager;

import com.github.enjektor.web.enums.HttpMethod;
import com.github.enjektor.web.servlet.collector.DefaultRequestPathInformationCollector;
import com.github.enjektor.web.servlet.collector.RequestPathInformationCollector;
import com.github.enjektor.web.state.*;
import com.github.enjektor.web.utils.hash.ByteHashProvider;
import com.github.enjektor.web.utils.hash.HashProvider;
import gnu.trove.map.TByteObjectMap;
import jakarta.servlet.http.HttpServletRequest;

import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PrimitiveEndpointManager implements EndpointManager {

    private static final RequestPathInformationCollector<String> requestPathInformationCollector = DefaultRequestPathInformationCollector.getInstance();
    private static final HashProvider hashProvider = ByteHashProvider.getInstance();

    @Override
    public HttpState process(RequestState requestState, HttpMethod httpMethod) {
        final HttpServletRequest request = requestState.getRequest();
        final String endpoint = requestPathInformationCollector.collect(request);
        final byte unsignedHashValue = hashProvider.provide(endpoint);

        final MethodState states = requestState.getState();
        final TByteObjectMap<Method> methods = states.getMethods();

        final Method methodThatWillInvoke = methods.get(unsignedHashValue);

        if (methodThatWillInvoke != null) {
            EndpointState parameterState = new EndpointState.Builder()
                    .method(methodThatWillInvoke)
                    .build();

            return new HttpState.Builder()
                    .parameterState(parameterState)
                    .build();
        } else {
            for (PathParameterState state : states.getStates()) {
                final Pattern pattern = state.getPattern();
                final Matcher matcher = pattern.matcher(endpoint);

                if (matcher.find()) {
                    final byte hashRegex = hashProvider.provide(state.getEndpoint());
                    final Method method = methods.get(hashRegex);

                    final EndpointState parameterState = new EndpointState.Builder()
                            .method(method)
                            .pattern(pattern)
                            .build();

                    return new HttpState.Builder()
                            .parameterState(parameterState)
                            .build();
                }
            }
        }
        return null;
    }
}
