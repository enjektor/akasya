package com.github.enjektor.web.servlet.endpoint;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface EndpointManager {
    void manageGet(HttpServletRequest req, HttpServletResponse res);
    void managePost(HttpServletRequest req, HttpServletResponse res);
    void manageDelete(HttpServletRequest req, HttpServletResponse res);
    void managePut(HttpServletRequest req, HttpServletResponse res);
}
