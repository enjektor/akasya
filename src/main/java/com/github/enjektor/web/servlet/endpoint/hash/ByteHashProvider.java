package com.github.enjektor.web.servlet.endpoint.hash;

import static com.github.enjektor.web.WebConstants.HASH_KEY;
import static com.github.enjektor.web.WebConstants.MASKING_VALUE;

public class ByteHashProvider implements HashProvider {

    private static HashProvider instance;

    private ByteHashProvider() {
    }

    @Override
    public byte provide(String endpoint) {
        return (byte) ((endpoint.hashCode() % HASH_KEY) & MASKING_VALUE);
    }

    public static HashProvider getInstance() {
        if (instance == null) instance = new ByteHashProvider();
        return instance;
    }
}
