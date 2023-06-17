package ma.sig.events.config;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class FeaturePolicyFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader(
            "Feature-Policy",
            "accelerometer 'none'; camera *; geolocation 'none'; " +
            "gyroscope 'none'; magnetometer 'none'; microphone 'none'; payment *; usb 'none'; " +
            "default-src 'self' data:;"
        );
        chain.doFilter(request, response);
    }
    // Implement the other methods of the Filter interface (init and destroy) if necessary

}
