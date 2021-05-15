package com.github.enjektor.akasya.invocation;

import com.github.enjektor.akasya.state.EndpointState;
import com.github.enjektor.akasya.state.RequestState;

public interface PathParameterInvocationHandler {
    void invoke(EndpointState pathParamState, RequestState requestState);
}
