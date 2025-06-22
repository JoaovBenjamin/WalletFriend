package com.joao.WalletFriend.security;

import com.joao.WalletFriend.model.Usuario.Usuario;
import com.joao.WalletFriend.repository.UsuarioRepository;
import com.joao.WalletFriend.service.Usuario.UsuarioServiceImpl;
import org.antlr.v4.runtime.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class AuthController {

    @Autowired
    AuthService service;

    @PostMapping("usuario/login")
    public JwtToken login(@RequestBody Credentials credentials){
        return service.login(credentials);
    }

    @PostMapping("usuario/register")
    public ResponseEntity<Usuario> newUser(@RequestBody Usuario usuario){
        service.novoUsuario(usuario);
        return ResponseEntity.status(HttpStatusCode.valueOf(201)).body(usuario);
    }
}
