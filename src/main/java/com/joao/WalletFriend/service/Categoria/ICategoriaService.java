package com.joao.WalletFriend.service.Categoria;

import com.joao.WalletFriend.model.Categoria.Categoria;
import com.joao.WalletFriend.model.Usuario.Usuario;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

public interface ICategoriaService {
     List<Categoria> listarCategorias();
     Optional<Categoria> buscarPorId(Long id);
     Categoria criarCategoria(Categoria categoria);
     Categoria alterarCategoira(Long id, Categoria categoria);
     void deletarCategoria(Long id);
     Optional<Categoria> buscarPorNome(String nome);
}
