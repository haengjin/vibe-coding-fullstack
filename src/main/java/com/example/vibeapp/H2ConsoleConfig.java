package com.example.vibeapp;

import jakarta.servlet.Servlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class H2ConsoleConfig {

    @Bean
    public ServletRegistrationBean<Servlet> h2ConsoleServlet() {
        Servlet servlet = createH2Servlet();
        ServletRegistrationBean<Servlet> registration = new ServletRegistrationBean<>(servlet, "/h2-console/*");
        registration.addInitParameter("webAllowOthers", "true");
        return registration;
    }

    private Servlet createH2Servlet() {
        String[] candidates = {
                "org.h2.server.web.JakartaWebServlet",
                "org.h2.server.web.WebServlet"
        };

        for (String className : candidates) {
            try {
                Class<?> clazz = Class.forName(className);
                Object instance = clazz.getDeclaredConstructor().newInstance();
                if (instance instanceof Servlet servlet) {
                    return servlet;
                }
            } catch (ReflectiveOperationException ignored) {
                // Try next candidate.
            }
        }

        throw new IllegalStateException("H2 console servlet class not found on runtime classpath.");
    }
}
