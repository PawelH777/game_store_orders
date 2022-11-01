package org.example.ordergames.infrastructure.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtFilter extends GenericFilterBean {

    @Override
    public void doFilter(final ServletRequest servletRequest, final ServletResponse servletResponse, final FilterChain filterChain) throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) servletRequest;
        final HttpServletResponse response = (HttpServletResponse) servletResponse;
        final String authHeader = request.getHeader("authorization");
        if ("OPTIONS".equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            filterChain.doFilter(request, response);
        } else {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing authorization");
                return;
            }
        }
        final String token = authHeader.substring(7);
        final Claims claims;
        try {
            claims = Jwts.parser().setSigningKey("secret").parseClaimsJws(token).getBody();
        } catch (final Exception ex) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Wrong authorization");
            return;
        }
        request.setAttribute("claims", claims);
        filterChain.doFilter(request, response);
    }
}
