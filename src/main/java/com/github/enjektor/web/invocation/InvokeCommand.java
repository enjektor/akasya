package com.github.enjektor.web.invocation;

import com.github.enjektor.web.state.EndpointState;
import com.github.enjektor.web.state.RequestState;

public interface InvokeCommand {
    void execute(EndpointState endpointState, RequestState requestState);
}
