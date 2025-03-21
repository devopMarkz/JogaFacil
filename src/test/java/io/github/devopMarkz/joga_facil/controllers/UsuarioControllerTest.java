package io.github.devopMarkz.joga_facil.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.devopMarkz.joga_facil.dtos.usuario.UsuarioRequestDTO;
import io.github.devopMarkz.joga_facil.dtos.usuario.UsuarioResponseDTO;
import io.github.devopMarkz.joga_facil.model.Role;
import io.github.devopMarkz.joga_facil.model.Usuario;
import io.github.devopMarkz.joga_facil.model.enums.RoleEnum;
import io.github.devopMarkz.joga_facil.services.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// @WebMvcTest(UsuarioController.class)
class UsuarioControllerTest {

    @MockitoBean
    private UsuarioService usuarioService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private UsuarioRequestDTO usuarioRequestDTO;

    private UsuarioResponseDTO usuarioResponseDTO;

    private Usuario usuario;

    @BeforeEach
    void setup(){
        usuario = new Usuario(null, "Marcos", "marcos@gmail.com", "123", "98986063363");
        usuarioRequestDTO = new UsuarioRequestDTO("Marcos", "marcos@gmail.com", "12345", "98986063303", null);
        usuarioResponseDTO = new UsuarioResponseDTO(null, "Marcos", "marcos@gmail.com", "98986063303", null);
    }

    @Test
    void cadastrarUsuario() throws Exception {
        // Arrange
        given(usuarioService.insert(usuarioRequestDTO)).willReturn(usuarioResponseDTO);

        // Act
        ResultActions response = mockMvc.perform(post("/usuario")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuarioRequestDTO)));

        // Assert
        response.andExpect(status().isForbidden());
    }
}