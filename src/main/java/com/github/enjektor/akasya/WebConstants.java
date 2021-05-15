package com.github.enjektor.akasya;

public class WebConstants {

    private WebConstants() {
        throw new AssertionError();
    }

    public static final byte HTTP_METHOD_GET = (byte) 0x0;
    public static final byte HTTP_METHOD_POST = (byte) 0x1;
    public static final byte HTTP_METHOD_DELETE = (byte) 0x2;
    public static final byte HTTP_METHOD_PUT = (byte) 0x3;
    public static final byte HASH_KEY = (byte) 0x7F;
    public static final byte MASKING_VALUE = (byte) 0xFF;
}
