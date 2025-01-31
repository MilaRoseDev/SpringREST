package com.mila.mobile.mobileNew.webSecurity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mila.mobile.mobileNew.model.request.UserLoginRequestModel;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    public AuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
            throws AuthenticationException {
        try {
            // Parse login credentials from the request body
            UserLoginRequestModel creds = new ObjectMapper().readValue(req.getInputStream(), UserLoginRequestModel.class);

            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(creds.getEmail(), creds.getPassword(), new ArrayList<>())
            );

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {

        // Generate a secure key using `Keys.hmacShaKeyFor`
        SecretKey secretKey = Keys.hmacShaKeyFor(SecurityConstants.TOKEN_SECRET.getBytes());

        // Retrieve the username from the authenticated user
        String userName = ((User) auth.getPrincipal()).getUsername();

        // Build the JWT with individual claims
        Instant now = Instant.now();
        JwtBuilder jwtBuilder = Jwts.builder()
                .claim("sub", userName) // Subject claim (username)
                .claim("iat", now.getEpochSecond()) // Issued at time
                .claim("exp", now.plusMillis(SecurityConstants.EXPIRATION_TIME).getEpochSecond()) // Expiration time
                .signWith(secretKey); // Sign the JWT with the secure key

        // Generate the JWT token
        String token = jwtBuilder.compact();

        // Add the token to the response header
        res.addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + token);
    }
}
