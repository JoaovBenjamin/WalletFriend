package com.joao.WalletFriend;

import com.joao.WalletFriend.model.Usuario.Usuario;
import com.joao.WalletFriend.repository.UsuarioRepository;
import com.joao.WalletFriend.security.AuthService;
import com.joao.WalletFriend.security.Credentials;
import com.joao.WalletFriend.security.JwtToken;
import com.joao.WalletFriend.security.JwtTokenUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class AuthServiceTest {
    @InjectMocks
    private AuthService serviceAuth;

    @Mock
    private UsuarioRepository repository;

    @Mock
    private JwtTokenUtil jwtTokenUtil;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("Teste de adicionar um novo usuario")
    public void casoAdicioneNovoUsuario(){

        //Arrange

        Usuario usuario = new Usuario();
        usuario.setEmail("joaovvbenjamin@gmail.com");
        usuario.setNome("João");
        usuario.setSenha("123456789");

        Usuario result = new Usuario();
        result.setId(1L);
        result.setSenha("123");
        result.setEmail("joaovvbenjamin@gmail");
        result.setNome("João");

        //Act
        Mockito.when(serviceAuth.novoUsuario(usuario)).thenReturn(result);

        //Asserts
        assertNotNull(result);
        assertEquals("João", result.getNome());
        assertEquals("joaovvbenjamin@gmail", result.getEmail());
    }

    @Test
    @DisplayName("Login correto tem que gerar um Token JWT")
    public void casoLoginCertoGereToken(){

        //Preparo como se fosse fazer o login no postman

        String email = "email123";
        String senha = "123456787";
        String senhaHash = "hash-da-senha-123456789";

        Usuario mock = new Usuario();
        mock.setEmail(email);
        mock.setSenha(senhaHash);

        //Set minhas credenciais
        Credentials credentials = new Credentials(email,senha);

        //Busco o usuario com o email
        Mockito.when(repository.findByEmail(email)).thenReturn(Optional.of(mock));

        //Quando o matches for chamaado eles vão retornar true para poder passar e gerar o token
        Mockito.when(passwordEncoder.matches(senha, senhaHash)).thenReturn(true);


        //Geramos o token manualmente para simularmos como seria no postman

        JwtToken token = new JwtToken("token-gerado","jwt");
        Mockito.when(jwtTokenUtil.createToken(email)).thenReturn(token);

        //Envio minha requisição
        JwtToken tokenLogin = serviceAuth.login(credentials);

        //Verifico os dados
        assertNotNull(tokenLogin);
        assertEquals("token-gerado", tokenLogin.token());
    }
}
