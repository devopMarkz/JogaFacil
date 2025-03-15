package io.github.devopMarkz.joga_facil.dtos.participantepartida;

import io.github.devopMarkz.joga_facil.dtos.partida.PartidaDTO;

import java.util.List;

public record ParticipantePartidaResponseDTO(
        PartidaDTO partidaResponseDTO,
        ParticipantePartidaDTO participantesPartidaDTO
) {
}
