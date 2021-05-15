package com.github.enjektor.akasya.invocation;

import com.github.enjektor.akasya.state.EndpointState;
import com.github.enjektor.akasya.state.RequestState;

public interface InvokeCommand {
    void execute(EndpointState endpointState, RequestState requestState);
}
