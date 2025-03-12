package io.github.devopMarkz.joga_facil.controllers;

import io.github.devopMarkz.joga_facil.dtos.partida.PartidaRequestDTO;
import io.github.devopMarkz.joga_facil.dtos.partida.PartidaResponseDTO;
import io.github.devopMarkz.joga_facil.services.impl.PartidaServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static io.github.devopMarkz.joga_facil.services.GenerateURIService.*;

@RestController
@RequestMapping("/partidas")
public class PartidaController {

    private PartidaServiceImpl partidaService;

    public PartidaController(PartidaServiceImpl partidaService) {
        this.partidaService = partidaService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ORGANIZADOR')")
    public ResponseEntity<PartidaResponseDTO> criarPartida(@RequestBody PartidaRequestDTO partidaRequestDTO){
        PartidaResponseDTO partida = partidaService.insert(partidaRequestDTO);
        return ResponseEntity.created(gerarURI(partida.id())).body(partida);
    }
}
