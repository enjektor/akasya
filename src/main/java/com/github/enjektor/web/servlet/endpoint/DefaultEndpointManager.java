package com.github.enjektor.web.servlet.endpoint;

import com.github.enjektor.web.servlet.endpoint.information.DefaultEndpointInformation;
import com.github.enjektor.web.servlet.endpoint.information.EndpointInformation;
import gnu.trove.map.TByteObjectMap;
import jakarta.servlet.http.HttpServletRequest;

import java.lang.reflect.Method;

public class DefaultEndpointManager implements EndpointManager {

    private final EndpointInformation<String> endpointInformation;
    private TByteObjectMap<Method>[] methods;

    public DefaultEndpointManager() {
        this.endpointInformation = DefaultEndpointInformation.getInstance();
    }

    @Override
    public void manageGet(HttpServletRequest req, HttpServletRequest res) {

    }

    @Override
    public void managePost(HttpServletRequest req, HttpServletRequest res) {

    }

    @Override
    public void manageDelete(HttpServletRequest req, HttpServletRequest res) {

    }

    @Override
    public void managePut(HttpServletRequest req, HttpServletRequest res) {

    }
}
