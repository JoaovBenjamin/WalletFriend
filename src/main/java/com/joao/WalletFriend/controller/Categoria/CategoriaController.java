package com.joao.WalletFriend.controller.Categoria;

import com.joao.WalletFriend.model.Categoria.Categoria;
import com.joao.WalletFriend.service.Categoria.CategoriaServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Tag(name = "Categorias")
public class CategoriaController {

    @Autowired
    CategoriaServiceImpl service;



    @GetMapping("categoria")
    @Operation(
            summary = "Retornar todas as categorias salvas",
            description = "Retorna um Array de categorias cadastradas pelo usuario atual"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Retorna o array com as categorias cadastradas"),
            @ApiResponse(responseCode = "401", description = "Usuario não autorizado"),
            @ApiResponse(responseCode = "403", description = "Usuario não autenticado")
    }
    )
    public List<Categoria> listarCategoria(){
        return service.listarCategorias();
    }

    @Parameters(
            @Parameter(description = "Jwt Token")
    )
    @Operation(
            summary = "Retornar a categoria salva referente ao seu id",
            description = "Retorna um Array de categoria cadastrada pelo usuario atual"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Retorna o array com a categoria cadastrada com o Id requisitado"),
            @ApiResponse(responseCode = "401", description = "Usuario não autorizado"),
            @ApiResponse(responseCode = "403", description = "Usuario não autenticado")
    }
    )
    @GetMapping("categoria/{id}")
    public Optional<Categoria> listarCategoriaPorId(@PathVariable Long id){
        return service.buscarPorId(id);
    }

    @Operation(
            summary = "Retornar a categoria salva referente ao nome da Categoria",
            description = "Retorna um Array de categoria cadastrada pelo usuario atual"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Retorna o array com a categoria cadastrada com o nome requisitado"),
            @ApiResponse(responseCode = "401", description = "Usuario não autorizado"),
            @ApiResponse(responseCode = "403", description = "Usuario não autenticado")

    }
    )
    @GetMapping("categoria/{nome}")
    public Optional<Categoria> buscarCategoriaPorNome(@RequestParam String nome){
        return service.buscarPorNome(nome);
    }

    @Operation(
            summary = "Cadastrar uma categoria pelo usuario",
            description = "Retorna um Objeto de Categoria cadastrado pelo usuario"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Categoria cadastrada"),
            @ApiResponse(responseCode = "401", description = "Usuario não autorizado"),
            @ApiResponse(responseCode = "400", description = "Falha na requisição"),
            @ApiResponse(responseCode = "403", description = "Usuario não autenticado")
    }
    )
    @Parameters(
            @Parameter(description = "Jwt Token")
    )
    @PostMapping("categoria")
    public ResponseEntity<Categoria> criarCategoria(@RequestBody Categoria categoria){
        service.criarCategoria(categoria);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoria);
    }

    @Operation(
            summary = "Altera uma categoria pelo usuario",
            description = "Retorna um Objeto de Categoria alterado pelo usuario"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Categoria atualizada com sucesso"),
            @ApiResponse(responseCode = "401", description = "Usuario não autorizado"),
            @ApiResponse(responseCode = "400", description = "Falha na requisição")
    }
    )
    @PutMapping("categoria")
    public Categoria editarCategoria(@PathVariable Long id, @RequestBody Categoria categoria){
        return service.alterarCategoira(id,categoria);
    }


    @Operation(
            summary = "Deletar uma categoria pelo usuario",
            description = "Retorna um objeto vazio"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Categoria deletada com sucesso"),
            @ApiResponse(responseCode = "401", description = "Usuario não autorizado"),
            @ApiResponse(responseCode = "400", description = "Falha na requisição")
    }
    )
    @DeleteMapping("categoria/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long id){
        service.deletarCategoria(id);
        return ResponseEntity.noContent().build();
    }
}
