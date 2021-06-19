package com.github.enjektor.web.servlet;

import com.github.enjektor.web.state.MethodState;
import gnu.trove.map.TByteObjectMap;


public interface ServletInitializer {
    TByteObjectMap<MethodState> initialize(Class<?> router);
}
