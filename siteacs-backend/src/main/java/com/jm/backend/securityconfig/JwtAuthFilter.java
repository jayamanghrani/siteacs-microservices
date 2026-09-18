package com.jm.backend.securityconfig;

import com.jm.backend.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthFilter extends OncePerRequestFilter
{
    private final JwtUtil jwtUtil;

    public JwtAuthFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            if (jwtUtil.isTokenValid(token)) {
                System.out.println("token is valid");
                String subject = jwtUtil.extractSubject(token);
                String role = jwtUtil.extractRole(token);

                var authToken = new UsernamePasswordAuthenticationToken(
                        subject, null, List.of(new SimpleGrantedAuthority(role))
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }

            // Agar token invalid hai, kuchh mat karo - bas aage badhne do
            // SecurityContext khaali rahega, jo "authenticated nahi hai" jaisa treat hoga
        }

        // Chahe token ho ya na ho, valid ho ya invalid - HAMESHA aage badhne do
        filterChain.doFilter(request, response);   // <-- YE HAMESHA CHALNA CHAHIYE
    }
}
