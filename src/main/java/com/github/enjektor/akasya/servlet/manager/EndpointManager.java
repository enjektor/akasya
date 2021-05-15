package com.github.enjektor.akasya.servlet.manager;

import com.github.enjektor.akasya.enums.HttpMethod;
import com.github.enjektor.akasya.state.HttpState;
import com.github.enjektor.akasya.state.RequestState;

public interface EndpointManager {
    HttpState process(RequestState requestState, HttpMethod httpMethod);
}
