package org.library.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {

        var token = jwtService.extractToken(request);
        if (token != null && jwtService.isTokenValid(token))
            if (SecurityContextHolder.getContext().getAuthentication() == null) {

                SecurityContext context = SecurityContextHolder.createEmptyContext();

                Authentication auth = jwtService.getAuthentication(token);
                context.setAuthentication(auth);
                SecurityContextHolder.setContext(context);
            }

        filterChain.doFilter(request, response);
    }
}
