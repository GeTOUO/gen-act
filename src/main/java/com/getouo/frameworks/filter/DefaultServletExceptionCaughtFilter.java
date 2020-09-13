package com.getouo.frameworks.filter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DefaultServletExceptionCaughtFilter extends OncePerRequestFilter implements ServletExceptionCaughtFilter {

    protected final Log logger = LogFactory.getLog(this.getClass());
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (Throwable tx) {
            String msg = "Failed to handle request'" + request.getRequestURI() + "': " + tx.getMessage();
            this.logger.error(msg, tx);
            request.setAttribute(EXCEPTION_ATTRIBUTE, tx);
            request.getRequestDispatcher(ERROR_RETHROW_PATH).forward(request, response);
        }
    }
}
