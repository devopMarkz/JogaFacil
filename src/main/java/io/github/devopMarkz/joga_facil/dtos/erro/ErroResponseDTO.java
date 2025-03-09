package io.github.devopMarkz.joga_facil.dtos.erro;

import java.time.Instant;
import java.util.List;

public record ErroResponseDTO(
    Instant timestamp,
    Integer status,
    String path,
    List<String> errors
) {
}
