package com.joao.WalletFriend.security;

import com.joao.WalletFriend.model.Usuario.Usuario;
import com.joao.WalletFriend.repository.UsuarioRepository;
import com.joao.WalletFriend.security.JwtToken;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Service
public class JwtTokenUtil {

    private final SecretKey secretKey;
    private final UsuarioRepository repository;

    public JwtTokenUtil(UsuarioRepository repository, @Value("${jwt.secret}") String secret) {
        this.repository = repository;
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public JwtToken createToken(String email){
        var expiraAt = LocalDateTime.now().plusHours(1).toInstant(ZoneOffset.ofHours(-3));

        String token = Jwts.builder()
                .subject(email)
                .issuer("Walletfriend")
                .issuedAt(new Date())
                .expiration(Date.from(expiraAt))
                .signWith(secretKey)
                .compact();

        return new JwtToken(token, "JWT");
    }

    public Usuario decode(String token){
        var email = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();

        return repository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario não encontrado"));
    }
}