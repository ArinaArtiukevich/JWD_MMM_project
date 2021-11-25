package com.jwd.controller.filter;

import com.jwd.controller.command.ParameterAttributeType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import java.io.IOException;

public class EncodingFilter implements Filter {
    private static final Logger LOGGER = LogManager.getLogger(EncodingFilter.class);
    private String encoding;

    @Override
    public void init(FilterConfig filterConfig) {
        LOGGER.info("Init FilterConfig filter");
        encoding = filterConfig.getInitParameter(ParameterAttributeType.ENCODING_INIT_PARAM_NAME);
        if (encoding == null) {
            encoding = ParameterAttributeType.DEFAULT_ENCODING;
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        LOGGER.debug("Start EncodingFilter");
        String contentType = request.getContentType();
        if (contentType != null && contentType.startsWith(ParameterAttributeType.FILTERABLE_CONTENT_TYPE)) {
            request.setCharacterEncoding(encoding);
        }
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}

