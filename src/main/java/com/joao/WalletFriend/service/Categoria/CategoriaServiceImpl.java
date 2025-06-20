package com.joao.WalletFriend.service.Categoria;

import com.joao.WalletFriend.model.Categoria.Categoria;
import com.joao.WalletFriend.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaServiceImpl implements ICategoriaService{

    @Autowired
    CategoriaRepository repository;

    @Override
    public List<Categoria> listarCategorias() {
        return repository.findAll();
    }

    @Override
    public Optional<Categoria> buscarPorId(Long id) {
        return repository.findById(id);
    }

    @Override
    public Optional<Categoria> buscarPorNome(String nome) {
        return repository.findByNomeIgnoreCase(nome);
    }

    @Override
    public Categoria criarCategoria(Categoria categoria) {
        return repository.save(categoria);
    }

    @Override
    public Categoria alterarCategoira(Long id, Categoria categoria) {
        verifyIfExists(id);
        categoria.setId(id);
        return repository.save(categoria);
    }

    @Override
    public void deletarCategoria(Long id) {
         verifyIfExists(id);
         repository.deleteById(id);
    }

    private void verifyIfExists(Long id) {
        repository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Não existe usuario com o id informado"));
    }
}
