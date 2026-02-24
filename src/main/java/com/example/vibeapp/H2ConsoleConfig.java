package com.example.vibeapp;

import org.h2.server.web.JakartaWebServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * H2 Console 서블릿 수동 등록
 * Spring Boot 4.x에서는 H2ConsoleAutoConfiguration이 동작하지 않을 수 있어
 * JakartaWebServlet을 직접 등록한다.
 */
@Configuration
public class H2ConsoleConfig {

    @Bean
    public ServletRegistrationBean<JakartaWebServlet> h2ConsoleServlet() {
        ServletRegistrationBean<JakartaWebServlet> registration = new ServletRegistrationBean<>(new JakartaWebServlet(),
                "/h2-console/*");
        registration.addInitParameter("webAllowOthers", "true");
        return registration;
    }
}
