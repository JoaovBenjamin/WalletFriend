package com.joao.WalletFriend.security;

import com.joao.WalletFriend.model.Usuario.Usuario;
import com.joao.WalletFriend.security.JwtTokenUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class MyFilter extends OncePerRequestFilter {

    private final JwtTokenUtil jwtTokenUtil;
    private final UserDetailsService userDetailsService; // ✅ Adicione isso

    public MyFilter(JwtTokenUtil jwtTokenUtil, UserDetailsService userDetailsService) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService; // ✅ Injete o UserDetailsService
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final String requestURI = request.getRequestURI();

        // ✅ Permite rotas públicas sem autenticação
        if (isPublicRoute(requestURI)) {
            filterChain.doFilter(request, response);
            return;
        }

        var header = request.getHeader("Authorization");

        if (header == null) {
            sendErrorResponse(response, "Authorization header is required");
            return;
        }

        if (!header.startsWith("Bearer ")) {
            sendErrorResponse(response, "Token must start with Bearer");
            return;
        }

        try {
            var token = header.replace("Bearer ", "").trim();
            Usuario user = jwtTokenUtil.decode(token);

            // ✅ Carrega UserDetails com authorities
            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());

            // ✅ Cria authentication com authorities
            var auth = new UsernamePasswordAuthenticationToken(
                    userDetails, // ✅ Use userDetails em vez de email/senha
                    null,        // ✅ Credentials como null (já autenticado via JWT)
                    userDetails.getAuthorities() // ✅ AUTHORITIES SÃO ESSENCIAIS!
            );

            SecurityContextHolder.getContext().setAuthentication(auth);
            filterChain.doFilter(request, response);

        } catch (Exception e) {
            sendErrorResponse(response, "Invalid token: " + e.getMessage());
        }
    }

    private boolean isPublicRoute(String requestURI) {
        return requestURI.startsWith("/usuario/register") ||
                requestURI.startsWith("/usuario/login") ||
                requestURI.startsWith("/swagger-ui") ||
                requestURI.startsWith("/v3/api-docs") ||
                requestURI.startsWith("/docs");
    }

    private void sendErrorResponse(HttpServletResponse response, String message)
            throws IOException {
        response.setStatus(401);
        response.setContentType("application/json");
        response.getWriter().write("{\"error\": \"" + message + "\"}");
    }
}