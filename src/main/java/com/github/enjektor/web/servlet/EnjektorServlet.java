package com.github.enjektor.web.servlet;

import com.github.enjektor.web.enums.HttpMethod;
import com.github.enjektor.web.invocation.*;
import com.github.enjektor.web.servlet.manager.DefaultEndpointManager;
import com.github.enjektor.web.servlet.manager.EndpointManager;
import com.github.enjektor.web.servlet.manager.PrimitiveEndpointManager;
import com.github.enjektor.web.state.HttpState;
import com.github.enjektor.web.state.MethodState;
import com.github.enjektor.web.state.EndpointState;
import com.github.enjektor.web.state.RequestState;
import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.lang.reflect.Method;

public class EnjektorServlet extends HttpServlet {

    private final EndpointManager endpointManager;
    private final TByteObjectMap<MethodState> stateMap;
    private final Object routerObject;
    private final InvocationHandler invocationHandler;
    private final PathParameterInvocationHandler pathParameterInvocationHandler;
    private final TByteObjectMap<InvokeCommand> invokeMap = new TByteObjectHashMap<>(2);

    public EnjektorServlet(final Object routerObject,
                           final Class<?> routerClass,
                           final ServletInitializer servletInitializer) {
        this.routerObject = routerObject;
        this.endpointManager = new PrimitiveEndpointManager();
        this.stateMap = servletInitializer.initialize(routerClass);
        this.invocationHandler = InvocationHandlerImpl.getInstance();
        this.pathParameterInvocationHandler = new PrimitivePathParameterInvocationHandler();
    }

    @Override
    public void init() throws ServletException {
        invokeMap.put((byte) 0, new DefaultInvokeCommand());
        invokeMap.put((byte) 1, new PathInvokeCommand());
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
        final EndpointState endpointState = httpState.getEndpointState();
        endpointState.setRouterObject(routerObject);

        // TODO: implement command pattern for method invocation
        final byte stateType = endpointState.stateType();
        invokeMap.get(stateType).execute(endpointState, requestState);
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
