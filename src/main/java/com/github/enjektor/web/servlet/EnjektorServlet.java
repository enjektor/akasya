package com.github.enjektor.web.servlet;

import com.github.enjektor.web.enums.HttpMethod;
import com.github.enjektor.web.servlet.manager.DefaultEndpointManager;
import com.github.enjektor.web.servlet.manager.EndpointManager;
import com.github.enjektor.web.state.HttpState;
import com.github.enjektor.web.state.MethodState;
import com.github.enjektor.web.state.RequestState;
import gnu.trove.map.TByteObjectMap;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class EnjektorServlet extends HttpServlet {

    private final Map<HttpMethod, BiConsumer<Object, HttpState>> actionMap = new HashMap<>(2);
    private final EndpointManager endpointManager;
    private final TByteObjectMap<MethodState> stateMap;
    private final Object routerObject;

    public EnjektorServlet(final Object routerObject,
                           final Class<?> routerClass,
                           final ServletInitializer servletInitializer) {
        this.routerObject = routerObject;
        this.endpointManager = new DefaultEndpointManager(routerObject, servletInitializer.initialize(routerClass));
        this.stateMap = servletInitializer.initialize(routerClass);
    }

    @Override
    protected void doGet(final HttpServletRequest req,
                         final HttpServletResponse resp) throws ServletException, IOException {
        final byte methodHex = HttpMethod.GET.getMethodHex();
        final MethodState methodState = stateMap.get(methodHex);

        final RequestState requestState = new RequestState.Builder()
                .request(req)
                .response(resp)
                .state(methodState)
                .build();

        final HttpState httpState = endpointManager.process(requestState, HttpMethod.GET);
        actionMap.get(HttpMethod.GET).accept(routerObject, httpState);
    }

    @Override
    protected void doPost(final HttpServletRequest req,
                          final HttpServletResponse resp) throws ServletException, IOException {
    }

    @Override
    protected void doDelete(final HttpServletRequest req,
                            final HttpServletResponse resp) throws ServletException, IOException {
    }

    @Override
    protected void doPut(final HttpServletRequest req,
                         final HttpServletResponse resp) throws ServletException, IOException {
    }

}
