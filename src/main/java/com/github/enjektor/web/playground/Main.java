package com.github.enjektor.web.playground;

import com.github.enjektor.core.auto.configuration.BeanAutoConfiguration;
import com.github.enjektor.core.bean.pair.Pair;
import com.github.enjektor.jdbc.EnjektorJdbcAutoConfiguration;
import com.github.enjektor.web.WebDependencyInitializer;
import com.github.enjektor.web.annotations.Router;
import com.github.enjektor.web.playground.middleware.Middleware;
import com.github.enjektor.web.playground.middleware.ServletMiddlewareWrapper;
import com.github.enjektor.web.servlet.DefaultServletInitializer;
import com.github.enjektor.web.servlet.EnjektorServlet;
import com.github.enjektor.web.servlet.ServletInitializer;
import com.github.enjektor.context.ApplicationContext;
import com.github.enjektor.context.PrimitiveApplicationContext;
import com.github.enjektor.context.dependency.ConcreteDependencyInitializer;
import com.github.enjektor.context.dependency.DependencyInitializer;
import com.github.enjektor.core.bean.Bean;
import jakarta.servlet.DispatcherType;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

public class Main {
    public static void main(String[] args) throws Exception {
        final DependencyInitializer dependencyInitializer = new ConcreteDependencyInitializer();
        final DependencyInitializer webDependenciesInitializer = new WebDependencyInitializer();
        final List<DependencyInitializer> dependencyInitializers = Arrays.asList(dependencyInitializer, webDependenciesInitializer);

        BeanAutoConfiguration beanAutoConfiguration = new EnjektorJdbcAutoConfiguration();
        Pair export = beanAutoConfiguration.export();

        final Map<Class<?>, Bean> beans = new WeakHashMap<>();
        beans.put(export.getType(), export.getBean());


        final ApplicationContext applicationContext = new PrimitiveApplicationContext(Main.class, dependencyInitializers, beans);

        final ExampleRouter bean = applicationContext.getBean(ExampleRouter.class);
        final ServletInitializer servletInitializer = new DefaultServletInitializer();

        EnjektorServlet defaultEnjektorServlet = new EnjektorServlet(bean, ExampleRouter.class, servletInitializer);

        Server server = new Server();
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(8080);

        server.setConnectors(new Connector[]{connector});

        ServletHolder holder = new ServletHolder();
        holder.setServlet(defaultEnjektorServlet);

        ServletHandler servletHandler = new ServletHandler();
        servletHandler.addServletWithMapping(holder, "/v1/*");

        for (FilterHolder filter : filters(applicationContext))
            servletHandler.addFilterWithMapping(filter, "/v1/*", EnumSet.of(DispatcherType.REQUEST));

        server.setHandler(servletHandler);
        server.start();
        System.gc();
    }

    public static List<FilterHolder> filters(ApplicationContext applicationContext) throws InstantiationException, IllegalAccessException {
        Router router = ExampleRouter.class.getAnnotation(Router.class);
        final Class<? extends Middleware>[] middlewares = router.middlewares();
        List<FilterHolder> list = new ArrayList<>(middlewares.length);

        for (Class<? extends Middleware> middleware : middlewares) {
            final Middleware bean = applicationContext.getBean(middleware);
            ServletMiddlewareWrapper servletMiddlewareWrapper = new ServletMiddlewareWrapper(bean);

            FilterHolder filterHolder = new FilterHolder();
            filterHolder.setFilter(servletMiddlewareWrapper);
            list.add(filterHolder);
        }

        return list;
    }


}
