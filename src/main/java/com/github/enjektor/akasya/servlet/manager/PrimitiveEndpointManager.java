package com.github.enjektor.akasya.servlet.manager;

import com.github.enjektor.akasya.enums.HttpMethod;
import com.github.enjektor.akasya.servlet.collector.DefaultRequestPathInformationCollector;
import com.github.enjektor.akasya.servlet.collector.RequestPathInformationCollector;
import com.github.enjektor.akasya.state.*;
import com.github.enjektor.akasya.utils.hash.ByteHashProvider;
import com.github.enjektor.akasya.utils.hash.HashProvider;
import gnu.trove.map.TByteObjectMap;
import jakarta.servlet.http.HttpServletRequest;

import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PrimitiveEndpointManager implements EndpointManager {

    private static final RequestPathInformationCollector<String> requestPathInformationCollector = DefaultRequestPathInformationCollector.getInstance();
    private static final HashProvider hashProvider = ByteHashProvider.getInstance();

    @Override
    public EndpointState process(RequestState requestState) {
        final HttpServletRequest request = requestState.getRequest();
        final String endpoint = requestPathInformationCollector.collect(request);
        final byte unsignedHashValue = hashProvider.provide(endpoint);

        final MethodState states = requestState.getState();
        final TByteObjectMap<Method> methods = states.getMethods();
        final Method methodThatWillInvoke = methods.get(unsignedHashValue);

        if (methodThatWillInvoke != null) {
            return new EndpointState.Builder()
                    .method(methodThatWillInvoke)
                    .build();
        } else {
            for (PathParameterState state : states.getStates()) {
                final Pattern pattern = state.getPattern();
                final Matcher matcher = pattern.matcher(endpoint);

                if (matcher.find()) {
                    final byte hashRegex = hashProvider.provide(state.getEndpoint());
                    final Method method = methods.get(hashRegex);

                    return new EndpointState.Builder()
                            .method(method)
                            .pattern(pattern)
                            .build();
                }
            }
        }

        return null;
    }
}
