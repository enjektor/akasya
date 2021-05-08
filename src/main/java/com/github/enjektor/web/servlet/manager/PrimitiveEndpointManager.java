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
            ParameterState parameterState = new ParameterState.Builder()
                    .method(methodThatWillInvoke)
                    .build();

            return new HttpState.Builder()
                    .parameterState(parameterState)
                    .requestState(requestState)
                    .build();
        } else {
            for (EndpointState state : states.getStates()) {
                final Pattern pattern = state.getPattern();
                final Matcher matcher = pattern.matcher(endpoint);

                if (matcher.find()) {
                    final byte hashRegex = hashProvider.provide(state.getEndpoint());
                    final Method method = methods.get(hashRegex);

                    final ParameterState parameterState = new ParameterState.Builder()
                            .method(method)
                            .pattern(pattern)
                            .build();

                    return new HttpState.Builder()
                            .parameterState(parameterState)
                            .requestState(requestState)
                            .build();
                }
            }
        }
        return null;
    }
}
