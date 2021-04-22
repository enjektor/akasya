package com.github.enjektor.web.playground.string;

import java.util.Arrays;

public class StringPlayground {
    public static void main(String[] args) {

        String pathInMethod = "/deploy-folder/{mapping}/request-path";
        String path = "/deploy-folder/servlet-mapping/request-path";

        final int startIndex = pathInMethod.indexOf("{mapping}");
        final String substring = path.substring(startIndex).split("/")[0];
        System.out.println(substring);


        final String calculate = calculate(pathInMethod);
        System.out.println("calculate = " + calculate);

        final String calculate1 = calculate(path);
    }

    private static String calculate(String p) {
        final String[] split = p.substring(1).split("/");
        byte[] index = new byte[split.length];

        byte count = (byte) 0;

        for (String s : split) {
            if (s.contains("{")) {
                index[count] = (byte) 0;
                count++;
                continue;
            }
            index[count] = (byte) 1;
            count++;
        }

        System.out.println(Arrays.toString(split));
        System.out.println(Arrays.toString(index));

        count = (byte) 0;
        StringBuilder stringBuilder = new StringBuilder();
        for (byte b : index)
            if (b == 1) stringBuilder.append(split[count++]);
            else count++;

        return stringBuilder.toString();
    }


}
