package jp.honto;

import com.google.common.collect.Maps;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.persist.PersistFilter;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.google.inject.servlet.GuiceServletContextListener;
import com.sun.jersey.guice.JerseyServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;

import java.util.Map;

/**
 * @author 5hun
 */
public class Main extends GuiceServletContextListener {
    @Override
    protected Injector getInjector() {
        return Guice.createInjector(
                new JerseyServletModule() {
                    @Override
                    protected void configureServlets() {
                        install(new JpaPersistModule("jp.honto.api"));
                        Map<String, String> params = Maps.newHashMap();
                        serve("/*").with(GuiceContainer.class, params);
                        filter("/*").through(PersistFilter.class);
                    }
                }
        );
    }
}
