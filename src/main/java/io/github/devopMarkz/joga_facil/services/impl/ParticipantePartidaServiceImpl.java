package io.github.devopMarkz.joga_facil.services.impl;

import io.github.devopMarkz.joga_facil.dtos.participantepartida.ParticipantePartidaResponseDTO;
import io.github.devopMarkz.joga_facil.dtos.partida.PartidaResponseDTO;
import io.github.devopMarkz.joga_facil.exceptions.ResourceNotFoundException;
import io.github.devopMarkz.joga_facil.model.ParticipantePartida;
import io.github.devopMarkz.joga_facil.model.ParticipantePartidaId;
import io.github.devopMarkz.joga_facil.model.Partida;
import io.github.devopMarkz.joga_facil.model.Usuario;
import io.github.devopMarkz.joga_facil.model.enums.StatusPagamento;
import io.github.devopMarkz.joga_facil.repositories.ParticipantePartidaRepository;
import io.github.devopMarkz.joga_facil.repositories.PartidaRepository;
import io.github.devopMarkz.joga_facil.repositories.UsuarioRepository;
import io.github.devopMarkz.joga_facil.services.exceptions.CodigoPartidaIncorretoException;
import io.github.devopMarkz.joga_facil.services.exceptions.LimiteDeParticipantesAtingidoException;
import io.github.devopMarkz.joga_facil.services.exceptions.OrganizadorInvalidoException;
import io.github.devopMarkz.joga_facil.services.exceptions.ParticipanteJaInscritoNaPartidaException;
import io.github.devopMarkz.joga_facil.utils.ObterUsuarioLogado;
import io.github.devopMarkz.joga_facil.utils.ParticipantePartidaMapper;
import io.github.devopMarkz.joga_facil.utils.PartidaMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ParticipantePartidaServiceImpl {

    private ParticipantePartidaRepository participantePartidaRepository;
    private PartidaRepository partidaRepository;
    private UsuarioRepository usuarioRepository;
    private ObterUsuarioLogado obterUsuarioLogado;
    private ParticipantePartidaMapper participantePartidaMapper;
    private PartidaMapper partidaMapper;

    public ParticipantePartidaServiceImpl(ParticipantePartidaRepository participantePartidaRepository, PartidaRepository partidaRepository, UsuarioRepository usuarioRepository, ObterUsuarioLogado obterUsuarioLogado, ParticipantePartidaMapper participantePartidaMapper, PartidaMapper partidaMapper) {
        this.participantePartidaRepository = participantePartidaRepository;
        this.partidaRepository = partidaRepository;
        this.usuarioRepository = usuarioRepository;
        this.obterUsuarioLogado = obterUsuarioLogado;
        this.participantePartidaMapper = participantePartidaMapper;
        this.partidaMapper = partidaMapper;
    }

    @Transactional
    public ParticipantePartidaResponseDTO insert(Long partidaId, String codigoPartida){
        Partida partida = partidaRepository.searchPartidaByIdWithParticipantes(partidaId)
                .orElseThrow(() -> new ResourceNotFoundException("Partida não encontrada."));

        if(!partida.getCodigoPartida().equals(codigoPartida)){
            throw new CodigoPartidaIncorretoException("Código de partida incorreto.");
        }

        long quantidadeDeParticipantes = participantePartidaRepository.countParticipantesByPartidaId(partidaId);

        if(quantidadeDeParticipantes >= partida.getVagasDisponiveis()){
            throw new LimiteDeParticipantesAtingidoException("Limite de participantes atingidos.");
        }

        Usuario participante = obterUsuarioLogado.obterUsuario();

        ParticipantePartida participantePartida = new ParticipantePartida(participante, partida);

        if (partida.getParticipantes().contains(participantePartida)){
            throw new ParticipanteJaInscritoNaPartidaException(participante.getEmail() + " já está inscrito na partida.");
        }

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

    @Transactional
    public void updatePagamentoParcicipante(Long partidaId, String participanteEmail){
        Partida partida = partidaRepository.searchPartidaByIdWithParticipantes(partidaId)
                .orElseThrow(() -> new ResourceNotFoundException("Partida não encontrada."));

        ParticipantePartida participantePartida = partida.getParticipantes().stream()
                .filter(p -> p.getUsuario().getEmail().equals(participanteEmail))
                .findAny()
                .orElseThrow(() -> new ResourceNotFoundException("Participante inexistente."));

        participantePartida.confirmarPagamentoEPresenca();

        partidaRepository.save(partida);
    }

    @Transactional(readOnly = true)
    public Page<PartidaResponseDTO> findByUserId(int pageNumber, int pageSize){
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        var usuario = obterUsuarioLogado.obterUsuario();
        Page<Partida> partidas = participantePartidaRepository.searchPartidasByParticipante(usuario.getId(), pageable);
        return partidas.map(partida -> partidaMapper.toDTO(partida));
    }

}
