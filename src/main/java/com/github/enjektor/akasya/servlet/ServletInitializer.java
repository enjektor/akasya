package com.github.enjektor.akasya.servlet;

import com.github.enjektor.akasya.state.MethodState;
import gnu.trove.map.TByteObjectMap;


public interface ServletInitializer {
    TByteObjectMap<MethodState> initialize(Class<?> router);
}
