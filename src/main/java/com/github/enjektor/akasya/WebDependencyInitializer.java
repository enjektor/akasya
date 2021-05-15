package com.github.enjektor.akasya;

import com.github.enjektor.context.bean.Bean;
import com.github.enjektor.context.dependency.DependencyInitializer;
import com.github.enjektor.core.exceptions.InvalidRouterNamingException;
import com.github.enjektor.core.scanner.AnnotationScanner;
import com.github.enjektor.akasya.annotations.Router;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class WebDependencyInitializer implements DependencyInitializer {

    @Override
    public Map<Class<?>, Bean> initialize(final Class<?> mainClass) {
        final AnnotationScanner annotationScanner = new AnnotationScanner();
        final Set<Class<?>> scannedClasses = annotationScanner.scan(mainClass, Router.class);
        final Map<Class<?>, Bean> routerMap = new HashMap<>();

        for (final Class<?> dependency : scannedClasses) {
            isInterface(dependency);
            final Bean bean = new Bean(dependency);
            bean.register(dependency);
            routerMap.put(bean.getClassType(), bean);
        }

        return routerMap;
    }

    private void isInterface(Class<?> bean) {
        if (bean.isInterface()) throw new InvalidRouterNamingException("Routers can not be interface, must be class.");
    }
}
