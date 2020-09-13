package com.getouo.frameworks.configuration;

import com.getouo.frameworks.filter.DefaultServletExceptionCaughtFilter;
import com.getouo.frameworks.filter.ServletExceptionCaughtFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.web.servlet.ConditionalOnMissingFilterBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;

@Configuration
public class HttpFilterConfiguration {

    @Bean
    @ConditionalOnMissingBean(ServletExceptionCaughtFilter.class)
    public ServletExceptionCaughtFilter servletExceptionCaughtFilter() {
        return new DefaultServletExceptionCaughtFilter();
    }

    @Bean
    @ConditionalOnBean(ServletExceptionCaughtFilter.class)
    // @ConditionalOnMissingFilterBean
    public FilterRegistrationBean<Filter> registerExceptionFilter(ServletExceptionCaughtFilter filter) {
        FilterRegistrationBean<Filter> bean = new FilterRegistrationBean();
        bean.setFilter(filter);
        bean.addUrlPatterns("/*");
        bean.setAsyncSupported(true);
        bean.setEnabled(true);
        bean.setDispatcherTypes(DispatcherType.REQUEST, DispatcherType.ASYNC);
        bean.setOrder(Integer.MIN_VALUE);
        return bean;
    }

}
