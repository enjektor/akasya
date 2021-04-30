package com.github.enjektor.web.playground;

import com.github.enjektor.core.annotations.Inject;
import com.github.enjektor.web.annotations.*;
import com.github.enjektor.web.playground.domain.Human;
import com.github.enjektor.web.playground.injectable.StringRandomizer;

@Router("/v1")
public class ExampleRouter {

    @Inject
    private StringRandomizer stringRandomizer;

    @Get
    public String health() {
        return "OK";
    }

    @Get("/end")
    public String executeOne() {
        return "end " + stringRandomizer.randomize();
    }

    @Post("/body")
    public Human getBody(@Body Human human) {
        human.setName("estimated");
        return human;
    }

    @Get("/b/{bodies}/another/{boi}")
    public int params(@Param("bodies") String bodies,
                      @Param("boi") String boiParam) {
        int x = 10;
        return x;
    }

    @Get("/api/employees/{id}/{name}")
    public void springExample(@Param("id") String id,
                              @Param("name") String name,
                              @Query("sort") String sort,
                              @Query("page") String page) {
        int y = 10;
    }
}
