package com.github.enjektor.web.servlet.manager;

import com.github.enjektor.web.enums.HttpMethod;
import com.github.enjektor.web.state.HttpState;
import com.github.enjektor.web.state.RequestState;

public interface EndpointManager {
    HttpState process(RequestState requestState, HttpMethod httpMethod);
}
