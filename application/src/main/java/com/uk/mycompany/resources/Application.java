package com.uk.mycompany.resources;

import com.uk.mycompany.filter.TestFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.uk.mycompany.controller"})
public class Application {

    @Bean
    public FilterRegistrationBean myFilterBean() {
        final FilterRegistrationBean filterRegBean = new FilterRegistrationBean();
        filterRegBean.setFilter(new TestFilter());
        filterRegBean.addUrlPatterns("/*");
        filterRegBean.setEnabled(Boolean.TRUE);
        filterRegBean.setName("TestFilter");
        filterRegBean.setAsyncSupported(Boolean.TRUE);
        return filterRegBean;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}