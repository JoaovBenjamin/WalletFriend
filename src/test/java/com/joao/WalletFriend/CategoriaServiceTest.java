package com.joao.WalletFriend;

import com.joao.WalletFriend.model.Categoria.Categoria;
import com.joao.WalletFriend.model.Usuario.Usuario;
import com.joao.WalletFriend.repository.CategoriaRepository;
import com.joao.WalletFriend.repository.UsuarioRepository;
import com.joao.WalletFriend.service.Categoria.CategoriaServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CategoriaServiceTest {

    @InjectMocks
    CategoriaServiceImpl service;

    @Mock
    CategoriaRepository repository;

    @Mock
    UsuarioRepository usuarioRepository;

    @Test
    @DisplayName("Teste para adicionar Categoria")
    public void casoAdicionarCategoria(){

        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setEmail("joaovvbenjamin@gmail");
        usuario.setSenha("1234566778");
        usuario.setNome("João");

        Categoria categoria = new Categoria();
        categoria.setIcone("12345");
        categoria.setNome("Netflix");
        categoria.setUser(usuario);

        Categoria resultC = new Categoria();
        resultC.setId(1L);
        resultC.setIcone("12345");
        resultC.setNome("Netflix");
        resultC.setUser(usuario);


        Mockito.when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        Mockito.when(repository.save(Mockito.any(Categoria.class))).thenReturn(resultC);
        Categoria resultado = service.criarCategoria(resultC);

        assertNotNull(resultado);
        assertEquals("Netflix",resultado.getNome());

        Mockito.verify(repository).save(Mockito.any(Categoria.class));

    }

    @Test
    @DisplayName("Teste de busca pelo id da categoria")
    public void casoBuscaPeloIdCategoria(){
        Long id = 1L;

        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setSenha("12345678");
        usuario.setEmail("joaovvbenjamin@gmail");
        usuario.setNome("João");

        Categoria categoria = new Categoria();
        categoria.setId(id);
        categoria.setNome("Netflix");
        categoria.setIcone("netflix");
        categoria.setUser(usuario);

        Mockito.when(service.buscarPorId(id)).thenReturn(Optional.of(categoria));

        assertNotNull(categoria);
        assertEquals("Netflix", categoria.getNome());
    }

    @Test
    @DisplayName("Teste de busca pelo nome da categoria")
    public void casoBuscaPeloNomeCategoria(){
        String nome = "netflix";

        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setEmail("joaovvbenjamin@gmail");
        usuario.setSenha("123456677");
        usuario.setNome("João");

        Categoria categoria = new Categoria();
        categoria.setId(1L);
        categoria.setNome("Netflix");
        categoria.setIcone("netflix");
        categoria.setUser(usuario);

        Mockito.when(repository.findByNomeIgnoreCase(nome)).thenReturn(Optional.of(categoria));

        assertNotNull(categoria);
        assertEquals("Netflix", categoria.getNome());
    }

    @Test
    @DisplayName("Teste para alterar a categoria")
    public void casoAltereCategoria(){

        Long id = 1L;

        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setSenha("12345678");
        usuario.setEmail("joaovvbenjamin@gmail");
        usuario.setNome("João");

        Categoria categoria = new Categoria();
        categoria.setId(id);
        categoria.setNome("Netflix");
        categoria.setIcone("netflix");
        categoria.setUser(usuario);

        Categoria categoriaNova = new Categoria();
        categoriaNova.setId(id);
        categoriaNova.setNome("Netflix-Atualizado");
        categoriaNova.setIcone("net");
        categoriaNova.setUser(usuario);


        Mockito.when(service.buscarPorId(id)).thenReturn(Optional.of(categoria));
        Mockito.when(repository.save(Mockito.any(Categoria.class)))
                .thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0));

        Categoria cat = service.alterarCategoira(id,categoriaNova);

        assertNotNull(cat);
        assertEquals("net",cat.getIcone());
        assertEquals("Netflix-Atualizado",cat.getNome());
    }

    @Test
    @DisplayName("Teste para deletar a categoria")
    public void casoDeleteCategoria(){
        Long id = 1L;

        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setEmail("joaovvbenjamin@gmail");
        usuario.setSenha("12345678");
        usuario.setNome("João");

        Categoria categoria = new Categoria();
        categoria.setId(id);
        categoria.setNome("Netflix");
        categoria.setIcone("netflix");
        categoria.setUser(usuario);

        Mockito.when(service.buscarPorId(id)).thenReturn(Optional.of(categoria));

        Mockito.doNothing().when(repository).deleteById(id);

        service.deletarCategoria(id);

        Mockito.verify(repository).deleteById(id);
    }
}
