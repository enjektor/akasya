package com.github.enjektor.web.invocation.parameter.helper;

public class PrimitiveParamRegexHelper implements ParamRegexHelper {

    /**
     * /v1/b/enes/another/feyza
     * /v1/b/{body}/another/{boi}
     * \/v1\/b\/(\w+)\/another\/(\w+)
     * @param endpoint
     * @return
     */

    @Override
    public String regex(String endpoint) {
        final StringBuilder stringBuilder = new StringBuilder();
        final String[] split = endpoint.substring(1).split("/");

        for (String each : split) {
            if (each.contains("{")) stringBuilder.append("\\/(\\w+)");
            else {
                stringBuilder.append("\\/").append(each);
            }
        }

        return stringBuilder.toString();
    }
}
