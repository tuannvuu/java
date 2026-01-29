package com.example.demo.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

        private final JwtService jwtService;
        private final UserDetailsService userDetailsService;

        public JwtAuthenticationFilter(
                        JwtService jwtService,
                        UserDetailsService userDetailsService) {
                this.jwtService = jwtService;
                this.userDetailsService = userDetailsService;
        }

        @Override
        protected boolean shouldNotFilter(@NonNull HttpServletRequest request) {
                String path = request.getRequestURI();
                String method = request.getMethod();

                // Auth public
                if (path.startsWith("/api/auth/")) {
                        return true;
                }

                // Preflight
                if ("OPTIONS".equalsIgnoreCase(method)) {
                        return true;
                }

                // Public GET products
                if ("GET".equalsIgnoreCase(method) && path.startsWith("/api/products")) {
                        return true;
                }

                return false;
        }

        @Override
        protected void doFilterInternal(
                        @NonNull HttpServletRequest request,
                        @NonNull HttpServletResponse response,
                        @NonNull FilterChain filterChain) throws ServletException, IOException {

                // 🔥 DEBUG
                System.out.println("[JWT] FILTER HIT → "
                                + request.getMethod() + " "
                                + request.getRequestURI());

                String authHeader = request.getHeader("Authorization");

                System.out.println("[JWT] Authorization header = " + authHeader);

                if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                        System.out.println("[JWT] ❌ NO TOKEN → pass filter");
                        filterChain.doFilter(request, response);
                        return;
                }

                String token = authHeader.substring(7);
                String username = jwtService.extractUsername(token);

                System.out.println("[JWT] username from token = " + username);

                if (username != null &&
                                SecurityContextHolder.getContext().getAuthentication() == null) {

                        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                        System.out.println("[JWT] UserDetails loaded = "
                                        + userDetails.getUsername());

                        if (jwtService.isTokenValid(token, userDetails)) {

                                List<String> roles = jwtService.extractRoles(token);

                                System.out.println("[JWT] roles from token = " + roles);

                                var authorities = roles.stream()
                                                .map(SimpleGrantedAuthority::new)
                                                .collect(Collectors.toList());

                                // 🔥 IN RA TỪNG AUTHORITY
                                System.out.println("[JWT] authorities size = " + authorities.size());
                                authorities.forEach(
                                                auth -> System.out.println("[JWT] authority = " + auth.getAuthority()));

                                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                                                userDetails, // Hoặc userDetails.getUsername()
                                                null,
                                                authorities);

                                authentication.setDetails(
                                                new WebAuthenticationDetailsSource()
                                                                .buildDetails(request));

                                SecurityContextHolder.getContext()
                                                .setAuthentication(authentication);

                                // 🔥 IN RA CHI TIẾT AUTHENTICATION
                                System.out.println("[JWT] ✅ Authentication SET");
                                System.out.println("[JWT] Principal = " + authentication.getPrincipal());
                                System.out.println("[JWT] Authorities = " + authentication.getAuthorities());
                                System.out.println("[JWT] isAuthenticated = " + authentication.isAuthenticated());

                        } else {
                                System.out.println("[JWT] ❌ TOKEN INVALID");
                        }
                }

                filterChain.doFilter(request, response);
        }
}
