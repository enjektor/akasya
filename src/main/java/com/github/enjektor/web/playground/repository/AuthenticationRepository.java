package com.github.enjektor.web.playground.repository;

import com.github.enjektor.core.annotations.Dependency;

@Dependency
public interface AuthenticationRepository {
    boolean isAuthenticated(String username, String password);
}
