package com.joao.WalletFriend.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.joao.WalletFriend.model.Usuario.Usuario;
import com.joao.WalletFriend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class JwtTokenUtil {
    private UsuarioRepository repository;
    private Algorithm algorithm;

    public JwtTokenUtil(UsuarioRepository repository,  @Value("${jwt.secret}")String secret) {
        algorithm= Algorithm.HMAC256(secret);
        this.repository = repository;
    }

    public JwtToken createToken(String email){
        var expiraAt = LocalDateTime.now().plusHours(1).toInstant(ZoneOffset.ofHours(-3));
        String token = JWT.create()
                .withSubject(email)
                .withIssuer("Walletfrind")
                .withExpiresAt(expiraAt)
                .sign(algorithm);

        return new JwtToken(token, "JWT");
    }

    public Usuario decode(String token){
        var email = JWT.require(algorithm)
                .build()
                .verify(token)
                .getSubject();

        return repository.findByEmail(email).
                orElseThrow(() -> new UsernameNotFoundException("Usuario não encontrado"));
    }
}
