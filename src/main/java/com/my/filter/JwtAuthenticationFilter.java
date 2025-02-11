package com.my.filter;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.my.services.JwtService;
import com.my.services.UserDetailsServiceImp;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsServiceImp userDetailsI;

    public JwtAuthenticationFilter(JwtService jwtService, UserDetailsServiceImp userDetailsI) {
        this.jwtService = jwtService;
        this.userDetailsI = userDetailsI;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
    
        String path = request.getRequestURI();
        return path.startsWith("/auth/login") || path.startsWith("/auth/register"); 
        // ✅ Skip authentication for login & register
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                    HttpServletResponse response, 
                                    FilterChain filterChain)
            throws ServletException, IOException {
    	System.out.println("Error in me filter");
        
        try {
            String authHeader = request.getHeader("Authorization");

            // ✅ If no token is present, continue request without modifying SecurityContext
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return;
            }

            String token = authHeader.substring(7);
            String username = jwtService.extractUsername(token);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsI.loadUserByUsername(username);

                // ✅ Validate token
                if (jwtService.isValid(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    // ✅ Set authentication in SecurityContext
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        } catch (Exception e) {
            System.out.println("⚠️ JWT Authentication failed: " + e.getMessage()); // ✅ Log error
            response.setStatus(HttpServletResponse.SC_FORBIDDEN); // ✅ Return 403 for invalid tokens
            return;
        }

        // ✅ Continue with the next filter
        filterChain.doFilter(request, response);
    }
}
