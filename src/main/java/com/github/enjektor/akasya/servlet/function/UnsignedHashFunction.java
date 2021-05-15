package com.github.enjektor.akasya.servlet.function;

import com.github.enjektor.akasya.servlet.collector.DefaultRequestPathInformationCollector;
import com.github.enjektor.akasya.servlet.collector.RequestPathInformationCollector;
import com.github.enjektor.akasya.utils.hash.ByteHashProvider;
import com.github.enjektor.akasya.utils.hash.HashProvider;
import jakarta.servlet.http.HttpServletRequest;

import java.util.function.Function;

public class UnsignedHashFunction implements Function<HttpServletRequest, Byte> {

    private final RequestPathInformationCollector<String> requestPathInformationCollector;
    private final HashProvider hashProvider;

    public UnsignedHashFunction() {
        this.requestPathInformationCollector = DefaultRequestPathInformationCollector.getInstance();
        this.hashProvider = ByteHashProvider.getInstance();
    }

    @Override
    public Byte apply(HttpServletRequest request) {
        final String endpoint = requestPathInformationCollector.collect(request);
        return hashProvider.provide(endpoint);
    }
}
