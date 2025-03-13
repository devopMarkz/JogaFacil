package io.github.devopMarkz.joga_facil.services.impl;

import io.github.devopMarkz.joga_facil.dtos.partida.PartidaRequestDTO;
import io.github.devopMarkz.joga_facil.dtos.partida.PartidaResponseDTO;
import io.github.devopMarkz.joga_facil.model.Partida;
import io.github.devopMarkz.joga_facil.model.Usuario;
import io.github.devopMarkz.joga_facil.repositories.PartidaRepository;
import io.github.devopMarkz.joga_facil.utils.ObterUsuarioLogado;
import io.github.devopMarkz.joga_facil.utils.PartidaMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PartidaServiceImpl {

    private PartidaRepository partidaRepository;
    private ObterUsuarioLogado obterUsuarioLogado;
    private PartidaMapper partidaMapper;

    public PartidaServiceImpl(PartidaRepository partidaRepository, ObterUsuarioLogado obterUsuarioLogado, PartidaMapper partidaMapper) {
        this.partidaRepository = partidaRepository;
        this.obterUsuarioLogado = obterUsuarioLogado;
        this.partidaMapper = partidaMapper;
    }

    @Transactional
    public PartidaResponseDTO insert(PartidaRequestDTO partidaRequestDTO){
        Usuario organizador = obterUsuarioLogado.obterUsuario();
        Partida partida = partidaMapper.toEntity(partidaRequestDTO, organizador);
        Partida novaPartida = partidaRepository.save(partida);
        return partidaMapper.toDTO(novaPartida);
    }

    @Transactional
    public Page<PartidaResponseDTO> findAll(Integer pageNumber, Integer pageSize){
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Partida> partidas = partidaRepository.findAll(pageable);
        return partidas.map(partida -> partidaMapper.toDTO(partida));
    }
}
