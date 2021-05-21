package com.github.enjektor.akasya.playground.injectable;

import com.github.enjektor.core.annotations.Dependency;

import java.util.concurrent.ThreadLocalRandom;

@Dependency
public interface StringRandomizer {
    String randomize();
}
