package com.joao.WalletFriend.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class MyFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        var header = request.getHeader("Authorization");

        if ( header == null){
                filterChain.doFilter(request,response);
                return;
        }

        if(!header.startsWith("Bearer")){
            response.setStatus(401);
            response.addHeader("Content-type", "application/json");
            response.getWriter().write("""
                        {"message": "Token deve começar com bearer"}
                    """);
            return;
        }
    }
}
