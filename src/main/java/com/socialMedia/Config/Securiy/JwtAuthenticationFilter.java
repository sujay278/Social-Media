package com.socialMedia.Config.Securiy;

import com.socialMedia.Utils.Constants;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = request.getHeader(Constants.AUTHORIZATION);

        if (token != null && token.startsWith(Constants.BEARER_)) {
            token = token.substring(7);
            String email = jwtUtil.extractEmail(token);

            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(email);
                if (jwtUtil.validateToken(token, userDetails.getUsername())) {
                    SecurityContextHolder.getContext().setAuthentication(
                            new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(
                                    userDetails, null, userDetails.getAuthorities()
                            )
                    );
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}

