package com.github.enjektor.akasya.playground;

import com.github.enjektor.akasya.annotations.Body;
import com.github.enjektor.akasya.annotations.Get;
import com.github.enjektor.akasya.annotations.Param;
import com.github.enjektor.akasya.annotations.Post;
import com.github.enjektor.akasya.annotations.Query;
import com.github.enjektor.akasya.annotations.Router;
import com.github.enjektor.core.annotations.Inject;
import com.github.enjektor.akasya.playground.domain.Human;
import com.github.enjektor.akasya.playground.injectable.StringRandomizer;

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
