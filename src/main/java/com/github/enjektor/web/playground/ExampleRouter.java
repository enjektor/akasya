package com.github.enjektor.web.playground;

import com.github.enjektor.core.annotations.Inject;
import com.github.enjektor.web.annotations.Body;
import com.github.enjektor.web.annotations.Get;
import com.github.enjektor.web.annotations.Param;
import com.github.enjektor.web.annotations.Post;
import com.github.enjektor.web.annotations.Router;
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
    public void params(@Param("bodies") String bodies,
                       @Param("boi") String boiParam) {
        int x = 10;
    }
}
