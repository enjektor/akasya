package com.github.enjektor.web.playground.injectable;

import com.github.enjektor.core.annotations.Dependency;

import java.util.concurrent.ThreadLocalRandom;

@Dependency
public class StringRandomizer {

    public String randomize() {
        char[] arr = new char[5];

        for (int i = 0; i < 5; i++) {
            int randomNum = ThreadLocalRandom.current().nextInt(65, 90);
            arr[i] = (char) randomNum;
        }
        return String.valueOf(arr);
    }

}
