package com.github.enjektor.web.playground.string;

public class StringPlayground {
    public static void main(String[] args) {

        String pathInMethod = "/deploy-folder/{mapping}/request-path";
        String path = "/deploy-folder/servlet-mapping/request-path";

        final int startIndex = pathInMethod.indexOf("{mapping}");
        final String substring = path.substring(startIndex).split("/")[0];
        System.out.println(substring);


        String pathInMethodTwo = "/human/cat/{robot}";
        String pathTwo = "/human/cat/ellipse";

        final int anotherStartIndex = pathInMethodTwo.indexOf("{robot}");
        final String aSub = pathTwo.substring(anotherStartIndex).split("/")[0];
        System.out.println(aSub);

    }

    private static int howSlashContains(String x) {
        int count = 0;
        final char[] chars = x.toCharArray();
        for (char y : chars) {
            if (y == '/') count++;
        }
        return count;
    }
}
