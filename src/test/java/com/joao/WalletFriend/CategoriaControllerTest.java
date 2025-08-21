package com.joao.WalletFriend.controller.Categoria;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.joao.WalletFriend.model.Categoria.Categoria;
import com.joao.WalletFriend.service.Categoria.CategoriaServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CategoriaController.class)
class CategoriaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CategoriaServiceImpl categoriaService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser
    void listarCategoria_DeveRetornar200() throws Exception {
        when(categoriaService.listarCategorias()).thenReturn(List.of(new Categoria()));

        mockMvc.perform(get("/categoria"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void listarCategoriaPorId_DeveRetornar200() throws Exception {
        Categoria categoria = new Categoria();
        categoria.setId(1L);

        when(categoriaService.buscarPorId(1L)).thenReturn(Optional.of(categoria));

        mockMvc.perform(get("/categoria/id/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @WithMockUser
    void criarCategoria_DeveRetornar201() throws Exception {
        Categoria categoria = new Categoria();
        categoria.setNome("Alimentação");

        when(categoriaService.criarCategoria(any(Categoria.class))).thenReturn(categoria);

        mockMvc.perform(post("/categoria")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoria)))
                .andExpect(status().isCreated());
    }
}