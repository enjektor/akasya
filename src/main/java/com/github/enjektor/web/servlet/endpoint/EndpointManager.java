package com.github.enjektor.web.servlet.endpoint;

import jakarta.servlet.http.HttpServletRequest;

public interface EndpointManager {
    void manageGet(HttpServletRequest req, HttpServletRequest res);
    void managePost(HttpServletRequest req, HttpServletRequest res);
    void manageDelete(HttpServletRequest req, HttpServletRequest res);
    void managePut(HttpServletRequest req, HttpServletRequest res);
}
