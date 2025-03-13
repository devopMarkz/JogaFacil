package io.github.devopMarkz.joga_facil.controllers;

import io.github.devopMarkz.joga_facil.dtos.partida.PartidaRequestDTO;
import io.github.devopMarkz.joga_facil.dtos.partida.PartidaResponseDTO;
import io.github.devopMarkz.joga_facil.services.impl.PartidaServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static io.github.devopMarkz.joga_facil.services.GenerateURIService.*;

@RestController
@RequestMapping("/partidas")
public class PartidaController {

    private PartidaServiceImpl partidaService;

    public PartidaController(PartidaServiceImpl partidaService) {
        this.partidaService = partidaService;
    }

    @GetMapping
    public ResponseEntity<Page<PartidaResponseDTO>> obterPartidasPorFiltros(
            @RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(name = "id", required = false) Long id,
            @RequestParam(name = "dataMinima", required = false) LocalDate dataMinima,
            @RequestParam(name = "dataMaxima", required = false) LocalDate dataMaxima
            ){
        Page<PartidaResponseDTO> partidas = partidaService.findByFilters(id, dataMinima, dataMaxima, pageNumber, pageSize);
        return ResponseEntity.ok(partidas);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ORGANIZADOR')")
    public ResponseEntity<PartidaResponseDTO> criarPartida(@RequestBody PartidaRequestDTO partidaRequestDTO){
        PartidaResponseDTO partida = partidaService.insert(partidaRequestDTO);
        return ResponseEntity.created(gerarURI(partida.id())).body(partida);
    }
}
