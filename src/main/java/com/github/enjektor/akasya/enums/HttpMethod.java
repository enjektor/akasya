package com.github.enjektor.akasya.enums;

public enum HttpMethod {
    GET((byte) 0x0),
    POST((byte) 0x1),
    PUT((byte) 0x2),
    DELETE((byte) 0x3);

    private final byte methodHex;

    HttpMethod(final byte methodHex) {
        this.methodHex = methodHex;
    }

    public byte getMethodHex() {
        return methodHex;
    }
}
