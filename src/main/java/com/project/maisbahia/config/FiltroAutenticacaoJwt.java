package com.project.maisbahia.config;

import java.io.IOException;

import com.project.maisbahia.services.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FiltroAutenticacaoJwt extends OncePerRequestFilter{

    @Autowired
    private TokenService autenticacaoService;

     @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);

            if (!autenticacaoService.isTokenValid(token)) {
                response.sendError(HttpStatus.UNAUTHORIZED.value(), "Token inválido ou revogado.");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
    
}
