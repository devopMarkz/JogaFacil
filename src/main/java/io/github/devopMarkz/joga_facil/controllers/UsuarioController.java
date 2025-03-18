package io.github.devopMarkz.joga_facil.controllers;

import io.github.devopMarkz.joga_facil.dtos.usuario.UsuarioRequestDTO;
import io.github.devopMarkz.joga_facil.dtos.usuario.UsuarioResponseDTO;
import io.github.devopMarkz.joga_facil.dtos.usuario.UsuarioUpdateSenhaDTO;
import io.github.devopMarkz.joga_facil.services.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static io.github.devopMarkz.joga_facil.services.GenerateURIService.gerarURI;

import java.net.URI;

@RestController
@RequestMapping("/usuario")
@Tag(
        name = "Operações de Usuário",
        description = "Endpoints para realizar alterações no usuário"
)
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    @Operation(summary = "Cadastra usuário", description = "Endpoint para efetuar cadastro de usuário")
    public ResponseEntity<Void> cadastrarUsuario(@Valid @RequestBody UsuarioRequestDTO usuarioRequestDTO){
        UsuarioResponseDTO usuarioResponseDTO = usuarioService.insert(usuarioRequestDTO);
        URI location = gerarURI(usuarioResponseDTO.id());
        return ResponseEntity.created(location).build();
    }

    @PutMapping
    @Operation(summary = "Atualiza usuário", description = "Endpoint para efetuar atualização de senha de usuário")
    public ResponseEntity<Void> atualizarSenha(@Valid @RequestBody UsuarioUpdateSenhaDTO usuarioUpdateSenhaDTO){
        usuarioService.updateSenha(usuarioUpdateSenhaDTO);
        return ResponseEntity.noContent().build();
    }

}
