package com.joao.WalletFriend;

import com.joao.WalletFriend.model.Usuario.Usuario;
import com.joao.WalletFriend.repository.UsuarioRepository;
import com.joao.WalletFriend.security.AuthService;
import com.joao.WalletFriend.service.Usuario.UsuarioServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UsuarioServiceTest {

    @InjectMocks
    private UsuarioServiceImpl service;

    @Mock
    UsuarioRepository repository;

    @Test
    @DisplayName("Teste de buscar por id")
    public void buscarUsuarioPorId(){

        //Arrange
        Long usuarioId = 1L;

        Usuario result = new Usuario();
        result.setEmail("joaovvbenjamin@gmail.com");
        result.setNome("João");
        result.setSenha("123456789");
        result.setId(usuarioId);

        //Act
        Mockito.when(service.buscarPorId(1L)).thenReturn(Optional.of(result));

        //Assert
        assertNotNull(result);
        assertEquals("João", result.getNome());
    }

    @Test
    @DisplayName("Teste de alterar usuario")
    public void alterarUsuario(){
        //Arrange
        Long usuarioId = 1L;

        Usuario usuarioExist = new Usuario();
        usuarioExist.setId(usuarioId);
        usuarioExist.setNome("João");
        usuarioExist.setSenha("12345678978");
        usuarioExist.setEmail("email");

        Usuario usuarioNovo = new Usuario();
        usuarioNovo.setNome("João");
        usuarioNovo.setSenha("12345378587837");
        usuarioNovo.setEmail("joaovvbenjamin@gmail.com");

        Mockito.when(service.buscarPorId(usuarioId)).thenReturn(Optional.of(usuarioExist));
        Mockito.when(repository.save(Mockito.any(Usuario.class))).thenAnswer(invocation -> invocation.getArgument(0));
        //Act
        Usuario usuarioAtualizado = service.editarUsuario(usuarioNovo,usuarioId);

        //Asserts

        assertNotNull(usuarioAtualizado);
        assertEquals("joaovvbenjamin@gmail.com",usuarioAtualizado.getEmail());

    }
    @Test
    @DisplayName("Teste para ver se o usuario é deletado")
    public void deletarUsuario(){

        //Arrange

        Long usuarioId = 1L;

        Usuario usuario = new Usuario();
        usuario.setEmail("joaovvbenjamin@gmail.com");
        usuario.setNome("João");
        usuario.setSenha("123333553");
        usuario.setId(usuarioId);

        Mockito.when(repository.findById(usuarioId))
                .thenReturn(Optional.of(usuario));

        Mockito.doNothing().when(repository).deleteById(usuarioId);

        service.deletarUsuario(usuarioId);

        Mockito.verify(repository).deleteById(usuarioId);
    }
}
