package com.github.enjektor.akasya.annotations;

import com.github.enjektor.akasya.playground.middleware.Middleware;

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

