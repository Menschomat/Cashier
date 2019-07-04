package de.menschomat.cashier.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTAuthenticationFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException {
        try {
            Authentication authentication = TokenAuthenticationService
                    .getAuthentication((HttpServletRequest) servletRequest);

            SecurityContextHolder.getContext().setAuthentication(authentication);

            filterChain.doFilter(servletRequest, servletResponse);

        } catch (Exception e) {
            ((HttpServletResponse) servletResponse).sendError(HttpServletResponse.SC_BAD_REQUEST, "BAD_CREDENTIALS");
        }

    }

}