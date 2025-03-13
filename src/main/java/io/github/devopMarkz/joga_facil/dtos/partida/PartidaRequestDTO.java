package io.github.devopMarkz.joga_facil.dtos.partida;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record PartidaRequestDTO(
        @NotNull(message = "O id é obrigatório.")
        @Min(value = 1, message = "O valor mínimo para ID é 1.")
        Long id,

        @NotNull(message = "A data e hora da partida são obrigatórias.")
        @Future(message = "A data da partida deve ser no futuro.")
        LocalDateTime dataHora,

        @NotBlank(message = "O local da partida é obrigatório.")
        String local,

        @NotNull(message = "O custo total da partida é obrigatório.")
        @Min(value = 0, message = "O custo total deve ser um valor positivo.")
        Double custoTotal,

        @NotNull(message = "O número de vagas disponíveis é obrigatório.")
        @Min(value = 1, message = "A partida deve ter pelo menos uma vaga disponível.")
        Integer vagasDisponiveis
) {
}
