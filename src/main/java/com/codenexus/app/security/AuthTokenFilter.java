package com.codenexus.app.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

/**
 * This filter intercepts every request to check for a valid JWT token.
 * It is the "Security Guard" of your application.
 */
@Component
@RequiredArgsConstructor
public class AuthTokenFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        try {
            // 1. Get the Authorization header from the request
            String header = request.getHeader("Authorization");

            // 2. Validate that the header contains a Bearer token
            if (header != null && header.startsWith("Bearer ")) {
                String token = header.substring(7);

                // 3. Validate the token string
                if (jwtUtils.validateJwtToken(token)) {
                    
                    // 4. Extract the user's email from the token
                    String email = jwtUtils.getUserNameFromJwtToken(token);

                    // 5. MASTER ROLE LOGIC: 
                    // If the email is ram@gmail.com, grant ADMIN privileges.
                    // Otherwise, grant standard STUDENT privileges.
                    String role = "ram@gmail.com".equals(email) ? "ROLE_ADMIN" : "ROLE_STUDENT";

                    // 6. Create the Authentication object for Spring Security
                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                            email, 
                            null, 
                            Collections.singletonList(new SimpleGrantedAuthority(role))
                    );

                    // 7. Attach request details to the authentication object
                    auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    // 8. Set the security context so the rest of the app knows who is logged in
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }
        } catch (Exception e) {
            // Log security errors if needed
            logger.error("Could not set user authentication: {}", e);
        }

        // 9. Continue with the rest of the filters
        filterChain.doFilter(request, response);
    }
}