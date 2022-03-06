package ie.ul.microservices.kernel.server.mapping;

import ie.ul.microservices.kernel.server.Constants;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This filter adds headers to the request/response, particularly headers that mark the request and response as being passed
 * through the gateway
 */
@Component
public class HeaderFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (servletResponse instanceof HttpServletResponse) {
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            response.setHeader(Constants.KERNEL_HEADER, "1");
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
