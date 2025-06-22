package com.joao.WalletFriend.service.Usuario;

import com.joao.WalletFriend.model.Usuario.Usuario;

import java.util.List;
import java.util.Optional;

public interface IUsuarioService {
    public List<Usuario> verUsuarios();
    public void deletarUsuario(Long id);
    public Usuario editarUsuario(Usuario usuario, Long id);
    public Optional<Usuario> buscarPorId(Long id);
}
