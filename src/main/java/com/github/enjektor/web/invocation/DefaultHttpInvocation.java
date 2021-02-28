package com.github.enjektor.web.invocation;

import com.github.enjektor.web.annotations.*;
import com.github.enjektor.web.servlet.ServletInitializerTuple;
import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

import java.lang.reflect.Method;

import static com.github.enjektor.web.constants.EnjektorWebConstants.HTTP_METHOD_GET;
import static com.github.enjektor.web.constants.EnjektorWebConstants.HTTP_METHOD_POST;
import static com.github.enjektor.web.constants.EnjektorWebConstants.HTTP_METHOD_DELETE;
import static com.github.enjektor.web.constants.EnjektorWebConstants.HTTP_METHOD_PUT;

public class DefaultHttpInvocation implements HttpInvocation {

    private static final int METHOD_NUMBER = 4;

    @Override
    public ServletInitializerTuple invoke(final Class<?> routerClass) {
        final TByteObjectMap<TByteObjectMap<Method>> methodMap = new TByteObjectHashMap<>();
        final Method[] declaredMethods = routerClass.getDeclaredMethods();
        final String[][] endpoints = new String[METHOD_NUMBER][declaredMethods.length];

        final Router router = routerClass.getAnnotation(Router.class);
        final String routerEndpoint = router.value().intern();

        final TByteObjectMap<Method> getMethods = new TByteObjectHashMap<>();
        final TByteObjectMap<Method> postMethods = new TByteObjectHashMap<>();
        final TByteObjectMap<Method> putMethods = new TByteObjectHashMap<>();
        final TByteObjectMap<Method> deleteMethods = new TByteObjectHashMap<>();

        byte getCounter = 0;
        byte postCounter = 0;
        byte deleteCounter = 0;
        byte putCounter = 0;

        for (final Method declaredMethod : declaredMethods) {
            final boolean isGet = declaredMethod.isAnnotationPresent(Get.class);
            if (isGet) {
                final Get getAnnotation = declaredMethod.getAnnotation(Get.class);
                final String endpointValue = routerEndpoint + getAnnotation.value();
                endpoints[HTTP_METHOD_GET - 1][getCounter] = endpointValue;
                getMethods.put(HTTP_METHOD_GET, declaredMethod);
                getCounter++;
            }

            final boolean isPost = declaredMethod.isAnnotationPresent(Post.class);
            if (isPost) {
                final Post postAnnotation = declaredMethod.getAnnotation(Post.class);
                final String endpointValue = routerEndpoint + postAnnotation.value();
                endpoints[HTTP_METHOD_POST - 1][postCounter] = endpointValue;
                postMethods.put(HTTP_METHOD_POST, declaredMethod);
                postCounter++;
            }

            final boolean isDelete = declaredMethod.isAnnotationPresent(Delete.class);
            if (isDelete) {
                final Delete deleteAnnotation = declaredMethod.getAnnotation(Delete.class);
                final String endpointValue = routerEndpoint + deleteAnnotation.value();
                endpoints[HTTP_METHOD_DELETE - 1][deleteCounter] = endpointValue;
                deleteMethods.put(HTTP_METHOD_DELETE, declaredMethod);
                deleteCounter++;
            }

            final boolean isPut = declaredMethod.isAnnotationPresent(Put.class);
            if (isPut) {
                final Put putAnnotation = declaredMethod.getAnnotation(Put.class);
                final String endpointValue = routerEndpoint + putAnnotation.value();
                endpoints[HTTP_METHOD_PUT - 1][putCounter] = endpointValue;
                putMethods.put(HTTP_METHOD_PUT, declaredMethod);
                putCounter++;
            }
        }

        methodMap.put(HTTP_METHOD_GET, getMethods);
        methodMap.put(HTTP_METHOD_POST, postMethods);
        methodMap.put(HTTP_METHOD_DELETE, deleteMethods);
        methodMap.put(HTTP_METHOD_PUT, putMethods);

        return new ServletInitializerTuple(methodMap, endpoints);
    }

}
