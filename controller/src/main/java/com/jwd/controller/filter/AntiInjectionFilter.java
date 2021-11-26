package com.jwd.controller.filter;

import com.jwd.controller.resources.ConfigurationBundle;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

import static com.jwd.controller.command.ParameterAttributeType.COMMAND_DOES_NOT_CONTAIN;
import static com.jwd.controller.util.Util.pathToJsp;

public class AntiInjectionFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        StringBuilder sb = new StringBuilder();
        Map<String, String[]> params = request.getParameterMap();
        for (String[] v : params.values()) {
            sb.append(v[0]);
        }
        if (sb.toString().trim().matches(COMMAND_DOES_NOT_CONTAIN)) {
            chain.doFilter(req, res);
        } else {
            request.getRequestDispatcher(pathToJsp(ConfigurationBundle.getProperty("path.page.error"))).forward(request, response);
        }
    }

    @Override
    public void destroy() {
    }

}