package io.github.devopMarkz.joga_facil.dtos.token;

import java.time.LocalDateTime;

public record TokenDTO(
        String access_token,
        LocalDateTime expires_in,
        String token_type
) {
}
