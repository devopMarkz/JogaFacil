package io.github.devopMarkz.joga_facil.services;

import io.github.devopMarkz.joga_facil.dtos.partida.PartidaRequestDTO;
import io.github.devopMarkz.joga_facil.dtos.partida.PartidaResponseDTO;
import org.springframework.data.domain.Page;

import java.time.LocalDate;

public interface PartidaService {

    PartidaResponseDTO insert(PartidaRequestDTO partidaRequestDTO);

    Page<PartidaResponseDTO> findByFilters(Long id, LocalDate dataMinima, LocalDate dataMaxima, Integer pageNumber, Integer pageSize);

    void update(PartidaRequestDTO partidaRequestDTO);

}
