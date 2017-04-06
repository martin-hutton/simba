package com.uk.mycompany.filter.config;

import com.uk.mycompany.filter.TestFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

/**
 * Created by mahutton on 03/04/2017.
 */

@Configuration
public class FilterConfig {

    // Now, confgure the Filter implemented above in Spring configuration class. Take a look
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

    @Bean(name = "TestFilter")
    public Filter someFilter() {
        return new TestFilter();
    }
}
