package com.github.enjektor.web.playground;

import com.github.enjektor.core.annotations.Inject;
import com.github.enjektor.web.annotations.Get;
import com.github.enjektor.web.annotations.Router;
import com.github.enjektor.web.playground.injectable.StringRandomizer;

@Router("/v1")
public class ExampleRouter {

    @Inject
    private StringRandomizer stringRandomizer;

    @Get
    public String execute() {
        return stringRandomizer.randomize();
    }

    @Get("/end")
    public String executeOne() {
        return "end " + stringRandomizer.randomize();
    }

}
