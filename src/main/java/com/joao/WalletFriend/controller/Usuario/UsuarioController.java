package com.joao.WalletFriend.controller.Usuario;

import com.joao.WalletFriend.model.Usuario.Usuario;
import com.joao.WalletFriend.service.Usuario.UsuarioServiceImpl;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@SecurityRequirement(name = "bearerAuth")
public class UsuarioController {
    @Autowired
    UsuarioServiceImpl service;

    @Operation(
           summary = "Buscar usuarios",
           description = "Retorna os as listas do objeto usuarios"
    )
    @ApiResponses(
            {
                    @ApiResponse(responseCode = "200", description = "Retorna lista de usuarios"),
                    @ApiResponse(responseCode = "401", description = "Usuario não autorizado"),
                    @ApiResponse(responseCode = "403", description = "Usuario não autenticado")

            }
    )
    @GetMapping("usuario")
    public List<Usuario> getUsuarios(){
        return service.verUsuarios();
    }


    @Operation(
            summary = "Alterar usuario",
            description = "Altera os dados do usuario e retorna o objeto usuario"
    )
    @ApiResponses(
            {
                    @ApiResponse(responseCode = "200", description = "Dados atualizados com sucesso"),
                    @ApiResponse(responseCode = "401", description = "Usuario não autorizado"),
                    @ApiResponse(responseCode = "400", description = "Erro de dados"),
                    @ApiResponse(responseCode = "403", description = "Usuario não autenticado")

            }
    )
    @PutMapping("usuario/{id}")
    public ResponseEntity<Usuario> editarUsuario(@PathVariable Long id, @RequestBody Usuario usuario){
        service.editarUsuario(usuario,id);
        return ResponseEntity.ok().body(usuario);
    }

    @Operation(
            summary = "Deletar usuario",
            description = "Deleta o objeto usuario e retorna um objeto vazio"
    )

    @ApiResponses(
            {
                    @ApiResponse(responseCode = "401", description = "Usuario não autorizado"),
                    @ApiResponse(responseCode = "204", description = "Usuario deletado"),
                    @ApiResponse(responseCode = "403", description = "Usuario não autenticado")

            }
    )
    @DeleteMapping("usuario/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id){
        service.deletarUsuario(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
