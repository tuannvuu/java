package com.example.demo.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
        protected boolean shouldNotFilter(HttpServletRequest request) {
                String path = request.getRequestURI();

                return path.startsWith("/api/auth/")
                                || (request.getMethod().equalsIgnoreCase("GET")
                                                && path.startsWith("/api/products"))
                                || request.getMethod().equalsIgnoreCase("OPTIONS");
        }

        @Override
        protected void doFilterInternal(
                        HttpServletRequest request,
                        HttpServletResponse response,
                        FilterChain filterChain)
                        throws ServletException, IOException {

                // 🔥 DEBUG: filter có chạy hay không
                System.out.println("[JWT] FILTER HIT → "
                                + request.getMethod() + " "
                                + request.getRequestURI());

                String authHeader = request.getHeader("Authorization");

                // 🔎 DEBUG Authorization header
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
                                                .map(role -> {
                                                        if (role.startsWith("ROLE_")) {
                                                                return new SimpleGrantedAuthority(role);
                                                        }
                                                        return new SimpleGrantedAuthority("ROLE_" + role);
                                                })
                                                .collect(Collectors.toList());

                                authorities.forEach(a -> System.out.println("[JWT] authority = " + a.getAuthority()));

                                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                                                userDetails,
                                                null,
                                                authorities);

                                authentication.setDetails(
                                                new WebAuthenticationDetailsSource()
                                                                .buildDetails(request));

                                SecurityContextHolder.getContext()
                                                .setAuthentication(authentication);

                                // ✅ DEBUG: authentication sau khi set
                                System.out.println("[JWT] ✅ Authentication SET = "
                                                + SecurityContextHolder.getContext().getAuthentication());
                        } else {
                                System.out.println("[JWT] ❌ TOKEN INVALID");
                        }
                } else {
                        System.out.println("[JWT] ❌ username null OR auth already exists");
                }

                filterChain.doFilter(request, response);
        }
}
