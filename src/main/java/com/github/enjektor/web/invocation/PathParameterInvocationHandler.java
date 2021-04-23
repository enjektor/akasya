package com.github.enjektor.web.invocation;

import com.github.enjektor.web.state.PathParamState;
import com.github.enjektor.web.state.RequestState;

public interface PathParameterInvocationHandler {
    void invoke(PathParamState pathParamState, RequestState requestState);
}
