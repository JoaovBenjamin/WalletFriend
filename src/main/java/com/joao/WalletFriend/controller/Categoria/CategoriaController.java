package com.joao.WalletFriend.controller.Categoria;

import com.joao.WalletFriend.model.Categoria.Categoria;
import com.joao.WalletFriend.model.Usuario.Usuario;
import com.joao.WalletFriend.repository.UsuarioRepository;
import com.joao.WalletFriend.service.Categoria.CategoriaServiceImpl;
import com.joao.WalletFriend.service.Usuario.UsuarioServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@SecurityRequirement(name = "bearerAuth")
@Slf4j
@RequestMapping("/categoria")
public class CategoriaController {

    @Autowired
    CategoriaServiceImpl service;

    @Autowired
    UsuarioServiceImpl usuarioService;



    @GetMapping()
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
    @GetMapping("/id/{id}")
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
    @GetMapping("/{nome}")
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

    @PostMapping()
    public ResponseEntity<Categoria> criarCategoria(@RequestBody Categoria categoria,
                                                    @AuthenticationPrincipal UserDetails userDetails) {
        Usuario usuario = usuarioService.buscarUsuarioPelaAuth(userDetails);
        categoria.setUser(usuario);
        Categoria categoriaSalva = service.criarCategoria(categoria);

        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaSalva);
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
    @PutMapping("/{id}")
    public ResponseEntity<Categoria> editarCategoria(@PathVariable Long id,
                                                     @RequestBody Categoria categoria,
                                                     @AuthenticationPrincipal UserDetails userDetails) {
        try {

            Usuario usuario = usuarioService.buscarUsuarioPelaAuth(userDetails);
            Optional<Categoria> categoriaExistente = service.buscarPorId(id);

            if (categoriaExistente.isEmpty() || !categoriaExistente.get().getUser().getId().equals(usuario.getId())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

            categoria.setUser(usuario);
            categoria.setId(id);
            Categoria categoriaAtualizada = service.alterarCategoira(id, categoria);
            return ResponseEntity.ok(categoriaAtualizada);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
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
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCategoria(@PathVariable Long id,
                                                 @AuthenticationPrincipal UserDetails userDetails) {
        try {
            Usuario usuario = usuarioService.buscarUsuarioPelaAuth(userDetails);

            Optional<Categoria> categoria = service.buscarPorId(id);
            if (categoria.isEmpty() || !categoria.get().getUser().getId().equals(usuario.getId())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

            service.deletarCategoria(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
