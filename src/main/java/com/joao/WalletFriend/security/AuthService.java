package com.joao.WalletFriend.security;

import com.joao.WalletFriend.model.Usuario.Usuario;
import com.joao.WalletFriend.repository.UsuarioRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class AuthService {

    UsuarioRepository repository;
    PasswordEncoder passwordEncoder;
    JwtTokenUtil jwtTokenUtil;

    public AuthService(UsuarioRepository repository, PasswordEncoder passwordEncoder, JwtTokenUtil jwtTokenUtil) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    public Usuario novoUsuario(@RequestBody Usuario usuario) {
        String novaSenha = passwordEncoder.encode(usuario.getSenha());
        usuario.setSenha(novaSenha);
        return repository.save(usuario);
    }

    public JwtToken login(Credentials credentials){
        Usuario res = repository.findByEmail(credentials.email()).orElseThrow(() ->
                new RuntimeException("Acesso negado"));
        if(!passwordEncoder.matches(credentials.senha(), res.getSenha())){
            throw new RuntimeException("Email ou senha invalidos");
        }
        return jwtTokenUtil.createToken(credentials.email());
    }
}
