package com.github.enjektor.web.invocation;

import com.github.enjektor.web.state.ParameterState;
import com.github.enjektor.web.state.RequestState;

public interface PathParameterInvocationHandler {
    void invoke(ParameterState pathParamState, RequestState requestState);
}
