package io.github.devopMarkz.joga_facil.controllers;

import io.github.devopMarkz.joga_facil.dtos.partida.PartidaRequestDTO;
import io.github.devopMarkz.joga_facil.dtos.partida.PartidaResponseDTO;
import io.github.devopMarkz.joga_facil.services.PartidaService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

import static io.github.devopMarkz.joga_facil.services.GenerateURIService.gerarURI;

@RestController
@RequestMapping("/partidas")
public class PartidaController {

    private PartidaService partidaService;

    public PartidaController(PartidaService partidaService) {
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
    public ResponseEntity<PartidaResponseDTO> criarPartida(@Valid @RequestBody PartidaRequestDTO partidaRequestDTO){
        PartidaResponseDTO partida = partidaService.insert(partidaRequestDTO);
        return ResponseEntity.created(gerarURI(partida.id())).body(partida);
    }

    @PutMapping
    @PreAuthorize("hasRole('ROLE_ORGANIZADOR')")
    public ResponseEntity<Void> atualizarPartida(@Valid @RequestBody PartidaRequestDTO partidaRequestDTO){
        partidaService.update(partidaRequestDTO);
        return ResponseEntity.noContent().build();
    }
}
