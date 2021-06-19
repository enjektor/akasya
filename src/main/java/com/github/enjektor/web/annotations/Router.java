package com.github.enjektor.web.annotations;

import com.github.enjektor.web.playground.middleware.Middleware;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Router {
    String value() default "";
    Class<? extends Middleware>[] middlewares() default {};
}

