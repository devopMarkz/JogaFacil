package io.github.devopMarkz.joga_facil.dtos.partida;

import java.time.LocalDateTime;

public record PartidaDTO(
        Long id,
        LocalDateTime dataHoraInicio,
        LocalDateTime dataHoraFim,
        String local,
        Double custoTotal,
        Integer vagasDisponiveis
) {
}
