package com.github.enjektor.web.tut;

import com.github.enjektor.web.annotations.Get;
import com.github.enjektor.web.annotations.Router;

@Router("/v1")
public class ExampleRouter {

    @Get
    public String execute() {
        return "yaptim!!";
    }

    @Get("/exclusive")
    public String executeOne() {
        return "yaptim laaaaan";
    }

}
