package io.github.devopMarkz.joga_facil.utils;

import io.github.devopMarkz.joga_facil.dtos.partida.PartidaRequestDTO;
import io.github.devopMarkz.joga_facil.dtos.partida.PartidaResponseDTO;
import io.github.devopMarkz.joga_facil.dtos.participantepartida.ParticipantePartidaDTO;
import io.github.devopMarkz.joga_facil.dtos.usuario.UsuarioOrganizadorDTO;
import io.github.devopMarkz.joga_facil.model.Partida;
import io.github.devopMarkz.joga_facil.model.Usuario;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PartidaMapper {

    public Partida toEntity(PartidaRequestDTO dto, Usuario organizador) {
        return new Partida(
                dto.dataHoraInicio(),
                dto.dataHoraFim(),
                dto.local(),
                dto.custoTotal(),
                dto.vagasDisponiveis(),
                organizador
        );
    }

    public PartidaResponseDTO toDTO(Partida partida) {
        UsuarioOrganizadorDTO organizadorDTO = new UsuarioOrganizadorDTO(
                partida.getOrganizador().getNome(),
                partida.getOrganizador().getEmail(),
                partida.getOrganizador().getTelefone()
        );

        List<ParticipantePartidaDTO> participantesDTO = partida.getParticipantes().stream()
                .map(participante -> new ParticipantePartidaDTO(
                        participante.getUsuario().getNome(),
                        participante.getUsuario().getEmail(),
                        participante.getUsuario().getTelefone(),
                        participante.getStatusPagamento().name(),
                        participante.getConfirmacaoPresenca()
                )).toList();

        return new PartidaResponseDTO(
                partida.getId(),
                partida.getDataHoraInicio(),
                partida.getDataHoraFim(),
                partida.getLocal(),
                partida.getCustoTotal(),
                partida.getVagasDisponiveis(),
                organizadorDTO,
                participantesDTO
        );
    }

    public Partida toEntityUpdated(PartidaRequestDTO dto, Partida partida){
        partida.setDataHoraInicio(dto.dataHoraInicio() == null? partida.getDataHoraInicio() : dto.dataHoraInicio());
        partida.setDataHoraFim(dto.dataHoraFim() == null? partida.getDataHoraFim() : dto.dataHoraFim());
        partida.setLocal(dto.local() == null? partida.getLocal() : dto.local());
        partida.setCustoTotal(dto.custoTotal() == null? partida.getCustoTotal() : dto.custoTotal());
        partida.setVagasDisponiveis(dto.vagasDisponiveis() == null? partida.getVagasDisponiveis() : dto.vagasDisponiveis());
        return partida;
    }

}
