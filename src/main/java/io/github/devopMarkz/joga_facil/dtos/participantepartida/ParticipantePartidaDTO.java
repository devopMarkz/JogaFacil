package io.github.devopMarkz.joga_facil.dtos.participantepartida;

public record ParticipantePartidaDTO(
        String nome,
        String email,
        String telefone,
        Double valorPagamento,
        String statusPagamento,
        Boolean confirmacaoPresenca
) {
}