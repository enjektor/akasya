package com.github.enjektor.web.invocation.http;

import com.github.enjektor.web.annotations.Delete;
import com.github.enjektor.web.annotations.Get;
import com.github.enjektor.web.annotations.Post;
import com.github.enjektor.web.annotations.Put;
import com.github.enjektor.web.annotations.Router;
import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

import java.lang.reflect.Method;

import static com.github.enjektor.web.WebConstants.HASH_KEY;
import static com.github.enjektor.web.WebConstants.HTTP_METHOD_DELETE;
import static com.github.enjektor.web.WebConstants.HTTP_METHOD_GET;
import static com.github.enjektor.web.WebConstants.HTTP_METHOD_POST;
import static com.github.enjektor.web.WebConstants.HTTP_METHOD_PUT;
import static com.github.enjektor.web.WebConstants.MASKING_VALUE;

public class DefaultHttpInvocation implements HttpInvocation {

    @Override
    public TByteObjectMap<Method>[] invoke(final Class<?> routerClass) {
        final TByteObjectMap<Method>[] methodArray = new TByteObjectMap[4];
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

        methodArray[HTTP_METHOD_GET] = getMethods;
        methodArray[HTTP_METHOD_POST] = postMethods;
        methodArray[HTTP_METHOD_DELETE] = deleteMethods;
        methodArray[HTTP_METHOD_PUT] = putMethods;

        return methodArray;
    }

    private byte unsignedHashValue(String endpoint) {
        return (byte) ((endpoint.hashCode() % HASH_KEY) & MASKING_VALUE);
    }

}
