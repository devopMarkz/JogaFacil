package io.github.devopMarkz.joga_facil.controllers;

import io.github.devopMarkz.joga_facil.dtos.partida.PartidaRequestDTO;
import io.github.devopMarkz.joga_facil.dtos.partida.PartidaResponseDTO;
import io.github.devopMarkz.joga_facil.services.impl.PartidaServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static io.github.devopMarkz.joga_facil.services.GenerateURIService.*;

@RestController
@RequestMapping("/partidas")
public class PartidaController {

    private PartidaServiceImpl partidaService;

    public PartidaController(PartidaServiceImpl partidaService) {
        this.partidaService = partidaService;
    }

    @GetMapping
    public ResponseEntity<Page<PartidaResponseDTO>> obterPartidas(
            @RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize
    ){
        Page<PartidaResponseDTO> partidas = partidaService.findAll(pageNumber, pageSize);
        return ResponseEntity.ok(partidas);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ORGANIZADOR')")
    public ResponseEntity<PartidaResponseDTO> criarPartida(@RequestBody PartidaRequestDTO partidaRequestDTO){
        PartidaResponseDTO partida = partidaService.insert(partidaRequestDTO);
        return ResponseEntity.created(gerarURI(partida.id())).body(partida);
    }
}
