package com.github.enjektor.web.playground.utils;

import com.github.enjektor.core.annotations.Dependency;

import java.util.Arrays;
import java.util.Base64;

@Dependency
public class Base64Utils {

    private static final Base64.Encoder encoder = Base64.getEncoder();
    private static final Base64.Decoder decoder = Base64.getDecoder();

    public String encode(String res) {
        return encoder.encodeToString(res.getBytes());
    }

    public String decode(String res) {
        return new String(decoder.decode(res));
    }
}
