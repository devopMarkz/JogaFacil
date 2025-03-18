package io.github.devopMarkz.joga_facil.services.impl;

import io.github.devopMarkz.joga_facil.dtos.participantepartida.ParticipantePartidaResponseDTO;
import io.github.devopMarkz.joga_facil.exceptions.ResourceNotFoundException;
import io.github.devopMarkz.joga_facil.model.ParticipantePartida;
import io.github.devopMarkz.joga_facil.model.ParticipantePartidaId;
import io.github.devopMarkz.joga_facil.model.Partida;
import io.github.devopMarkz.joga_facil.model.Usuario;
import io.github.devopMarkz.joga_facil.repositories.ParticipantePartidaRepository;
import io.github.devopMarkz.joga_facil.repositories.PartidaRepository;
import io.github.devopMarkz.joga_facil.repositories.UsuarioRepository;
import io.github.devopMarkz.joga_facil.services.exceptions.LimiteDeParticipantesAtingidoException;
import io.github.devopMarkz.joga_facil.services.exceptions.OrganizadorInvalidoException;
import io.github.devopMarkz.joga_facil.utils.ObterUsuarioLogado;
import io.github.devopMarkz.joga_facil.utils.ParticipantePartidaMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ParticipantePartidaServiceImpl {

    private ParticipantePartidaRepository participantePartidaRepository;
    private PartidaRepository partidaRepository;
    private UsuarioRepository usuarioRepository;
    private ObterUsuarioLogado obterUsuarioLogado;
    private ParticipantePartidaMapper participantePartidaMapper;

    public ParticipantePartidaServiceImpl(ParticipantePartidaRepository participantePartidaRepository, PartidaRepository partidaRepository, UsuarioRepository usuarioRepository, ObterUsuarioLogado obterUsuarioLogado, ParticipantePartidaMapper participantePartidaMapper) {
        this.participantePartidaRepository = participantePartidaRepository;
        this.partidaRepository = partidaRepository;
        this.usuarioRepository = usuarioRepository;
        this.obterUsuarioLogado = obterUsuarioLogado;
        this.participantePartidaMapper = participantePartidaMapper;
    }

    @Transactional
    public ParticipantePartidaResponseDTO insert(Long partidaId){
        Partida partida = partidaRepository.searchPartidaByIdWithParticipantes(partidaId)
                .orElseThrow(() -> new ResourceNotFoundException("Partida não encontrada."));

        long quantidadeDeParticipantes = participantePartidaRepository.countParticipantesByPartidaId(partidaId);

        if(quantidadeDeParticipantes >= partida.getVagasDisponiveis()){
            throw new LimiteDeParticipantesAtingidoException("Limite de participantes atingidos.");
        }

        Usuario participante = obterUsuarioLogado.obterUsuario();

        ParticipantePartida participantePartida = new ParticipantePartida(participante, partida);

        partida.getParticipantes().add(participantePartida);
        partida.subtrairVagasDisponiveis();
        partida.atualizarValorAPagar();

        Partida partidaAtualizada = partidaRepository.save(partida);

        return participantePartidaMapper.toDTO(partida.getParticipantes().getLast());
    }

    @Transactional
    public void deleteParticipanteByPartidaId(String participanteEmail, Long partidaId){
        Partida partida = partidaRepository.findById(partidaId)
                .orElseThrow(() -> new ResourceNotFoundException("Partida inexistente."));

        Usuario organizador = obterUsuarioLogado.obterUsuario();

        if(!organizador.equals(partida.getOrganizador())){
            throw new OrganizadorInvalidoException(organizador.getEmail() + " não é organizador dessa partida.");
        }

        Usuario usuario = usuarioRepository.searchByEmail(participanteEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário inexistente."));

        ParticipantePartidaId participantePartidaId = new ParticipantePartidaId(usuario.getId(), partida.getId());

        ParticipantePartida participantePartida = participantePartidaRepository.findById(participantePartidaId)
                .orElseThrow(() -> new ResourceNotFoundException("Participante não encontrado."));

        partida.getParticipantes().remove(participantePartida);

        partida.adicionarVagasDisponiveis();
        partida.atualizarValorAPagar();

        partidaRepository.save(partida);
    }

}
