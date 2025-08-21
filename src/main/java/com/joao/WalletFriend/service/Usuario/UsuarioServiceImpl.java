package com.joao.WalletFriend.service.Usuario;

import com.joao.WalletFriend.model.Usuario.Usuario;
import com.joao.WalletFriend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements IUsuarioService {
    @Autowired
    UsuarioRepository repository;


    @Override
    public void deletarUsuario(Long id) {
        verifyIfExists(id);
        repository.deleteById(id);
    }

    @Override
    public Usuario editarUsuario(Usuario usuario, Long id) {
        verifyIfExists(id);
        usuario.setId(id);
        return repository.save(usuario);
    }

    @Override
    public List<Usuario> verUsuarios() {
        return repository.findAll();
    }

    @Override
    public Optional<Usuario> buscarPorId(Long id) {
        return repository.findById(id);
    }

    private void verifyIfExists(Long id) {
        repository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Não existe usuario com o id informado"));
    }

    @Override
    public Usuario buscarUsuarioPelaAuth(UserDetails userDetails) {
        String email = userDetails.getUsername();
        return repository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Email não encotrado" + email));

    }
}
