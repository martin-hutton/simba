package com.uk.mycompany.filter;

// Blog post: http://www.ricardogiaviti.com.br/2016/11/18/criando-e-configurando-filtros-no-spring-boot/

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * First, define and implement the Filter
 *
 * @author Ricardo Giaviti
 **/
@Component
@Order(1)
public class TestFilter implements Filter {

    /**
     * Logging todo o request da aplicação para auditoria
     */

    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {
        int i = 0;
    }

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException {
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        int i = 0;
    }


}