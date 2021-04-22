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
        final String[] split = endpoint.split("/");
        final StringBuilder stringBuilder = new StringBuilder();

        for (String each : split) {
            if (each.contains("{")) stringBuilder.append("\\/\\w+");
            else {
                
            }
        }

        return null;
    }
}
