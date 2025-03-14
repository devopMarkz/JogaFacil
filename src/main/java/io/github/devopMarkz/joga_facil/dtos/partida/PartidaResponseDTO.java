package io.github.devopMarkz.joga_facil.dtos.partida;

import io.github.devopMarkz.joga_facil.dtos.participantepartida.ParticipantePartidaDTO;
import io.github.devopMarkz.joga_facil.dtos.usuario.UsuarioOrganizadorDTO;
import java.time.LocalDateTime;
import java.util.List;

public record PartidaResponseDTO(
        Long id,
        LocalDateTime dataHoraInicio,
        LocalDateTime dataHoraFim,
        String local,
        Double custoTotal,
        Integer vagasDisponiveis,
        UsuarioOrganizadorDTO organizador,
        List<ParticipantePartidaDTO> participantes
) {
}
