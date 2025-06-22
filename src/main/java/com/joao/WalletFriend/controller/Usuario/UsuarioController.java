package com.joao.WalletFriend.controller.Usuario;

import com.joao.WalletFriend.model.Usuario.Usuario;
import com.joao.WalletFriend.service.Usuario.UsuarioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UsuarioController {
    @Autowired
    UsuarioServiceImpl service;

    @GetMapping("usuario")
    public List<Usuario> getUsuarios(){
        return service.verUsuarios();
    }


    @PutMapping("usuario/{id}")
    public ResponseEntity<Usuario> editarUsuario(@PathVariable Long id, @RequestBody Usuario usuario){
        service.editarUsuario(usuario,id);
        return ResponseEntity.ok().body(usuario);
    }

    @DeleteMapping("usuario/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id){
        service.deletarUsuario(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
