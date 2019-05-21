package top.codelab.website.web.filter;

import org.apache.logging.log4j.CloseableThreadContext;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

public class MDCFilter extends OncePerRequestFilter {

    public static final String MDC_UUID = "uuid";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try (final CloseableThreadContext.Instance ctc = CloseableThreadContext.put(MDC_UUID, UUID.randomUUID().toString())) {
            filterChain.doFilter(request, response);
        }
    }
}
