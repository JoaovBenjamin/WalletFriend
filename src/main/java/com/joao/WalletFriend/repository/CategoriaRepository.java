package com.joao.WalletFriend.repository;

import com.joao.WalletFriend.model.Categoria.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    Optional<Categoria> findByNomeIgnoreCase(String name);
}
