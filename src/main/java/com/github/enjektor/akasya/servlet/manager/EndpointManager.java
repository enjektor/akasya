package com.github.enjektor.akasya.servlet.manager;

import com.github.enjektor.akasya.state.EndpointState;
import com.github.enjektor.akasya.state.RequestState;

public interface EndpointManager {
    EndpointState process(RequestState requestState);
}
