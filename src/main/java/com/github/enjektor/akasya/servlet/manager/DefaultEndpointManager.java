package com.github.enjektor.akasya.servlet.manager;

public class DefaultEndpointManager {
//        implements EndpointManager {

//    private final EndpointInformation<String> endpointInformation;
//    private final HashProvider hashProvider;
//    private final InvocationHandler invocationHandler;
//    private final PathParameterInvocationHandler pathParameterInvocationHandler;
//    private final MethodState GET_STATE;
//    private final Object routerObject;
//
//    public DefaultEndpointManager(final Object routerObject,
//                                  final TByteObjectMap<MethodState> methods) {
//        this.routerObject = routerObject;
//        this.invocationHandler = InvocationHandlerImpl.getInstance();
//        this.endpointInformation = DefaultEndpointInformation.getInstance();
//        this.hashProvider = ByteHashProvider.getInstance();
//        this.GET_STATE = methods.get(HTTP_METHOD_GET);
//        this.pathParameterInvocationHandler = PrimitivePathParameterInvocationHandler.getInstance();
//    }
//
//
//    /**
//     * \/v1\/b\/(\w+)\/another\/(\w+)
//     * /v1/b/enes/another/feyza
//     * /v1/b/{body}/another/{boi}
//     */
//    @Override
//    public void manageGet(HttpServletRequest req, HttpServletResponse res) {
//        final String endpoint = endpointInformation.collectInformation(req);
//        final byte unsignedHashValue = hashProvider.provide(endpoint);
//        final Method methodThatWillExecute = GET_STATE.getMethods().get(unsignedHashValue);
//
//        if (methodThatWillExecute != null) {
//            invocationHandler.invoke(routerObject, methodThatWillExecute, req, res);
//        } else {
//            for (PathParameterState state : GET_STATE.getStates()) {
//                final Pattern pattern = state.getPattern();
//                final Matcher matcher = pattern.matcher(endpoint);
//                if (matcher.find()) {
//                    byte hashRegex = hashProvider.provide(state.getEndpoint());
//                    final Method method = GET_STATE.getMethods().get(hashRegex);
//
//                    EndpointState pathParamState = new EndpointState.Builder()
//                            .method(method)
//                            .pattern(pattern)
//                            .build();
//
//                    RequestState requestState = new RequestState.Builder()
//                            .request(req)
//                            .response(res)
//                            .build();
//
//                    pathParameterInvocationHandler.invoke(pathParamState, requestState);
//                }
//            }
//        }
//    }
//
//    @Override
//    public void managePost(HttpServletRequest req, HttpServletResponse res) {
////        final String endpoint = endpointInformation.collectInformation(req);
////        final byte unsignedHashValue = hashProvider.provide(endpoint);
////        final Method methodThatWillExecute = methods[HTTP_METHOD_POST].get(unsignedHashValue).getMethod();
////        invocationHandler.invoke(routerObject, methodThatWillExecute, req, res);
//    }
//
//    @Override
//    public void manageDelete(HttpServletRequest req, HttpServletResponse res) {
//
//    }
//
//    @Override
//    public void managePut(HttpServletRequest req, HttpServletResponse res) {
//
//    }


}
