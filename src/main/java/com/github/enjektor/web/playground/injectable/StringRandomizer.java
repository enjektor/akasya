package com.github.enjektor.web.playground.injectable;

import com.github.enjektor.core.annotations.Dependency;

@Dependency
public interface StringRandomizer {
    String randomize();
}
