package com.github.enjektor.web.invocation;

import com.github.enjektor.web.state.EndpointState;
import com.github.enjektor.web.state.RequestState;

public interface PathParameterInvocationHandler {
    void invoke(EndpointState pathParamState, RequestState requestState);
}
