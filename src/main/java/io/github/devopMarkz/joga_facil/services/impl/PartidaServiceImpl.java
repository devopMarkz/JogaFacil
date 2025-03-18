package io.github.devopMarkz.joga_facil.services.impl;

import io.github.devopMarkz.joga_facil.dtos.partida.PartidaRequestDTO;
import io.github.devopMarkz.joga_facil.dtos.partida.PartidaResponseDTO;
import io.github.devopMarkz.joga_facil.exceptions.ResourceNotFoundException;
import io.github.devopMarkz.joga_facil.model.Partida;
import io.github.devopMarkz.joga_facil.model.Usuario;
import io.github.devopMarkz.joga_facil.repositories.ParticipantePartidaRepository;
import io.github.devopMarkz.joga_facil.repositories.PartidaRepository;
import io.github.devopMarkz.joga_facil.services.PartidaService;
import io.github.devopMarkz.joga_facil.services.exceptions.OrganizadorInvalidoException;
import io.github.devopMarkz.joga_facil.utils.ObterUsuarioLogado;
import io.github.devopMarkz.joga_facil.utils.PartidaMapper;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class PartidaServiceImpl implements PartidaService {

    private PartidaRepository partidaRepository;
    private ObterUsuarioLogado obterUsuarioLogado;
    private PartidaMapper partidaMapper;
    private ParticipantePartidaRepository participantePartidaRepository;

    public PartidaServiceImpl(PartidaRepository partidaRepository, ObterUsuarioLogado obterUsuarioLogado, PartidaMapper partidaMapper, ParticipantePartidaRepository participantePartidaRepository) {
        this.partidaRepository = partidaRepository;
        this.obterUsuarioLogado = obterUsuarioLogado;
        this.partidaMapper = partidaMapper;
        this.participantePartidaRepository = participantePartidaRepository;
    }

    @Override
    @Transactional
    public PartidaResponseDTO insert(PartidaRequestDTO partidaRequestDTO){
        Usuario organizador = obterUsuarioLogado.obterUsuario();
        Partida partida = partidaMapper.toEntity(partidaRequestDTO, organizador);
        Partida novaPartida = partidaRepository.save(partida);
        return partidaMapper.toDTO(novaPartida);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PartidaResponseDTO> findByFilters(Long id, LocalDate dataMinima, LocalDate dataMaxima, Integer pageNumber, Integer pageSize){
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        LocalDateTime dataHoraMinima = (dataMinima == null)? LocalDateTime.now() : dataMinima.atStartOfDay();
        LocalDateTime dataHoraMaxima = (dataMaxima == null)? null : dataMaxima.atStartOfDay();

        Page<Partida> partidas = partidaRepository.searchByFilters(id, dataHoraMinima, dataHoraMaxima, pageable);

        return partidas.map(partida -> partidaMapper.toDTO(partida));
    }

    @Override
    @Transactional
    public void update(PartidaRequestDTO partidaRequestDTO){
        Partida partida = partidaRepository.findById(partidaRequestDTO.id())
                .orElseThrow(() -> new ResourceNotFoundException("Partida inexistente."));

        Usuario usuario = obterUsuarioLogado.obterUsuario();

        if(!partida.getOrganizador().equals(usuario)){
            throw new OrganizadorInvalidoException(usuario.getEmail() + " não é o organizador desta partida.");
        }

        partidaMapper.toEntityUpdated(partidaRequestDTO, partida);

        Integer quantidadeDeVagas = partida.getVagasDisponiveis();
        Long quantidadeDeParticipantes = participantePartidaRepository.countParticipantesByPartidaId(partida.getId());
        Integer vagasDisponiveis = quantidadeDeVagas - quantidadeDeParticipantes.intValue();

        partida.setVagasDisponiveis(vagasDisponiveis);

        partida.atualizarValorAPagar();

        partidaRepository.save(partida);
    }

}
