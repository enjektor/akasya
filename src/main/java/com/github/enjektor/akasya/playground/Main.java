package com.github.enjektor.akasya.playground;

import com.github.enjektor.context.ApplicationContext;
import com.github.enjektor.context.PrimitiveApplicationContext;
import com.github.enjektor.context.dependency.DefaultDependencyInitializer;
import com.github.enjektor.context.dependency.DependencyInitializer;
import com.github.enjektor.akasya.WebDependencyInitializer;
import com.github.enjektor.akasya.servlet.DefaultServletInitializer;
import com.github.enjektor.akasya.servlet.EnjektorServlet;
import com.github.enjektor.akasya.servlet.ServletInitializer;
import com.github.enjektor.akasya.servlet.filter.SimpleServletFilter;
import com.google.common.collect.ImmutableList;
import jakarta.servlet.DispatcherType;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.util.EnumSet;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        final DependencyInitializer dependencyInitializer = new DefaultDependencyInitializer();
        final DependencyInitializer webDependencyInitializer = new WebDependencyInitializer();
        final List<DependencyInitializer> dependencyInitializers = ImmutableList.of(dependencyInitializer, webDependencyInitializer);

        final ApplicationContext applicationContext = new PrimitiveApplicationContext(Main.class, dependencyInitializers);
        final ExampleRouter bean = applicationContext.getBean(ExampleRouter.class);

        final ServletInitializer servletInitializer = new DefaultServletInitializer();

        EnjektorServlet defaultEnjektorServlet = new EnjektorServlet(bean, ExampleRouter.class, servletInitializer);

        Server server = new Server();
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(8080);

        server.setConnectors(new Connector[] {connector});

        ServletHolder holder = new ServletHolder();
        holder.setServlet(defaultEnjektorServlet);

        ServletHandler servletHandler = new ServletHandler();
        servletHandler.addServletWithMapping(holder, "/v1/*");
        servletHandler.addFilterWithMapping(SimpleServletFilter.class, "/v1/*", EnumSet.of(DispatcherType.REQUEST));

        server.setHandler(servletHandler);
        server.start();
        System.gc();
    }
}
