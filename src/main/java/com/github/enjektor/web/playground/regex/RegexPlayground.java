package com.github.enjektor.web.playground.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexPlayground {
    public static void main(String[] args) {

        /**
         * \/v1\/b\/(\w+)\/another\/(\w+)
         * /v1/b/enes/another/feyza
         * /v1/b/{body}/another/{boi}
         */

        Pattern p = Pattern.compile("\\/v1\\/b\\/(\\w+)\\/another\\/(\\w+)");
        Matcher matcher = p.matcher("/v1/b/enes/another/feyza");

        if (matcher.find()) {
            final String group = matcher.group(1);
            final String group1 = matcher.group(2);

            System.out.println("group = " + group);
            System.out.println("group1 = " + group1);
        }

    }
}
