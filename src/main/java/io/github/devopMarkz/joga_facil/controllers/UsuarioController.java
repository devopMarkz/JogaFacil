package io.github.devopMarkz.joga_facil.controllers;

import io.github.devopMarkz.joga_facil.dtos.usuario.UsuarioRequestDTO;
import io.github.devopMarkz.joga_facil.dtos.usuario.UsuarioResponseDTO;
import io.github.devopMarkz.joga_facil.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static io.github.devopMarkz.joga_facil.services.GenerateURIService.gerarURI;

import java.net.URI;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<Void> cadastrarUsuario(@Valid @RequestBody UsuarioRequestDTO usuarioRequestDTO){
        UsuarioResponseDTO usuarioResponseDTO = usuarioService.insert(usuarioRequestDTO);
        URI location = gerarURI(usuarioResponseDTO.id());
        return ResponseEntity.created(location).build();
    }

}
