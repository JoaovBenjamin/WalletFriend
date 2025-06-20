package com.joao.WalletFriend.controller.Categoria;

import com.joao.WalletFriend.model.Categoria.Categoria;
import com.joao.WalletFriend.service.Categoria.CategoriaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class CategoriaController {

    @Autowired
    CategoriaServiceImpl service;

    @GetMapping("categoria")
    public List<Categoria> listarCategoria(){
        return service.listarCategorias();
    }

    @GetMapping("categoria/{id}")
    public Optional<Categoria> listarCategoriaPorId(@PathVariable Long id){
        return service.buscarPorId(id);
    }

    @GetMapping("categoria/{nome}")
    public Optional<Categoria> buscarCategoriaPorId(@RequestParam String nome){
        return service.buscarPorNome(nome);
    }

    @PostMapping("categoria")
    public ResponseEntity<Categoria> criarCategoria(@RequestBody Categoria categoria){
        service.criarCategoria(categoria);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoria);
    }

    @PutMapping("categoria")
    public Categoria editarCategoria(@PathVariable Long id, @RequestBody Categoria categoria){
        return service.alterarCategoira(id,categoria);
    }

    @DeleteMapping("categoria/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long id){
        service.deletarCategoria(id);
        return ResponseEntity.noContent().build();
    }
}
