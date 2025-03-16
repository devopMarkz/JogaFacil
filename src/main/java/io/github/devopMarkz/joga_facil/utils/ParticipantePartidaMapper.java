package io.github.devopMarkz.joga_facil.utils;

import io.github.devopMarkz.joga_facil.dtos.participantepartida.ParticipantePartidaDTO;
import io.github.devopMarkz.joga_facil.dtos.participantepartida.ParticipantePartidaResponseDTO;
import io.github.devopMarkz.joga_facil.dtos.partida.PartidaDTO;
import io.github.devopMarkz.joga_facil.model.ParticipantePartida;
import io.github.devopMarkz.joga_facil.model.Partida;
import io.github.devopMarkz.joga_facil.model.Usuario;
import io.github.devopMarkz.joga_facil.repositories.PartidaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ParticipantePartidaMapper {

    private PartidaRepository partidaRepository;

    public ParticipantePartidaMapper(PartidaRepository partidaRepository) {
        this.partidaRepository = partidaRepository;
    }

    public ParticipantePartidaResponseDTO toDTO(ParticipantePartida participantePartida){
        Usuario participante = participantePartida.getUsuario();
        Partida partida = participantePartida.getPartida();

        PartidaDTO partidaDTO = new PartidaDTO(
                partida.getId(),
                partida.getDataHoraInicio(),
                partida.getDataHoraFim(),
                partida.getLocal(),
                partida.getCustoTotal(),
                partida.getVagasDisponiveis()
        );

        ParticipantePartidaDTO participantePartidaDTO = new ParticipantePartidaDTO(
                participante.getNome(),
                participante.getEmail(),
                participante.getTelefone(),
                participantePartida.getValorPagamento(),
                participantePartida.getStatusPagamento().name(),
                participantePartida.getConfirmacaoPresenca()
        );

        return new ParticipantePartidaResponseDTO(
                partidaDTO,
                participantePartidaDTO
        );
    }

}
