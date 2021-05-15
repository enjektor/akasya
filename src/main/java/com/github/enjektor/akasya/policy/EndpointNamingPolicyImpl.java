package com.github.enjektor.akasya.policy;

public class EndpointNamingPolicyImpl implements EndpointNamingPolicy {

    /**
     * Decimal 47
     */
    private static final byte SLASH_CHARACTER = (byte) 0x2F;
    private static EndpointNamingPolicy instance = null;

    private EndpointNamingPolicyImpl() {

    }

    @Override
    public String erase(final String endpoint) {
        final char[] chars = endpoint.toCharArray();

        if ((byte) chars[chars.length - 1] == SLASH_CHARACTER) {
            char[] erased = new char[chars.length - 1];
            System.arraycopy(chars, 0, erased, 0, chars.length - 1);
            return String.valueOf(erased);
        } else {
            return endpoint;
        }
    }

    public static EndpointNamingPolicy getInstance() {
        if (instance == null) instance = new EndpointNamingPolicyImpl();
        return instance;
    }
}
