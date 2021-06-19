package com.github.enjektor.web.servlet.manager;

import com.github.enjektor.web.state.EndpointState;
import com.github.enjektor.web.state.RequestState;

public interface EndpointManager {
    EndpointState process(RequestState requestState);
}
