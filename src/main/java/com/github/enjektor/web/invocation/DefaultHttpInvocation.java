package com.github.enjektor.web.invocation;

import com.github.enjektor.web.annotations.*;
import com.github.enjektor.web.constants.EnjektorWebConstants;
import com.github.enjektor.web.servlet.ServletInitializerTuple;
import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

import java.lang.reflect.Method;


public class DefaultHttpInvocation implements HttpInvocation, EnjektorWebConstants {

    @Override
    public ServletInitializerTuple invoke(final Class<?> routerClass) {
        final TByteObjectMap<TByteObjectMap<Method>> methodMap = new TByteObjectHashMap<>();
        final Method[] declaredMethods = routerClass.getDeclaredMethods();

        final Router router = routerClass.getAnnotation(Router.class);
        final String routerEndpoint = router.value().intern();

        final TByteObjectMap<Method> getMethods = new TByteObjectHashMap<>();
        final TByteObjectMap<Method> postMethods = new TByteObjectHashMap<>();
        final TByteObjectMap<Method> putMethods = new TByteObjectHashMap<>();
        final TByteObjectMap<Method> deleteMethods = new TByteObjectHashMap<>();


        for (final Method declaredMethod : declaredMethods) {
            final boolean isGet = declaredMethod.isAnnotationPresent(Get.class);
            if (isGet) {
                final Get getAnnotation = declaredMethod.getAnnotation(Get.class);
                final String endpointValue = routerEndpoint + getAnnotation.value();
                final byte hashValue = unsignedHashValue(endpointValue);
                getMethods.put(hashValue, declaredMethod);
            }

            final boolean isPost = declaredMethod.isAnnotationPresent(Post.class);
            if (isPost) {
                final Post postAnnotation = declaredMethod.getAnnotation(Post.class);
                final String endpointValue = routerEndpoint + postAnnotation.value();
                final byte hashValue = unsignedHashValue(endpointValue);
                postMethods.put(hashValue, declaredMethod);
            }

            final boolean isDelete = declaredMethod.isAnnotationPresent(Delete.class);
            if (isDelete) {
                final Delete deleteAnnotation = declaredMethod.getAnnotation(Delete.class);
                final String endpointValue = routerEndpoint + deleteAnnotation.value();
                final byte hashValue = unsignedHashValue(endpointValue);
                deleteMethods.put(hashValue, declaredMethod);
            }

            final boolean isPut = declaredMethod.isAnnotationPresent(Put.class);
            if (isPut) {
                final Put putAnnotation = declaredMethod.getAnnotation(Put.class);
                final String endpointValue = routerEndpoint + putAnnotation.value();
                final byte hashValue = unsignedHashValue(endpointValue);
                putMethods.put(hashValue, declaredMethod);
            }
        }

        methodMap.put(HTTP_METHOD_GET, getMethods);
        methodMap.put(HTTP_METHOD_POST, postMethods);
        methodMap.put(HTTP_METHOD_DELETE, deleteMethods);
        methodMap.put(HTTP_METHOD_PUT, putMethods);

        return new ServletInitializerTuple(methodMap);
    }

    private byte unsignedHashValue(String endpoint) {
        return (byte) ((endpoint.hashCode() % HASH_KEY) & MASKING_VALUE);
    }

}
