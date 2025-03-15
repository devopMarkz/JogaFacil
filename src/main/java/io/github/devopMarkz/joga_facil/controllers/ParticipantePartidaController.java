package io.github.devopMarkz.joga_facil.controllers;

import io.github.devopMarkz.joga_facil.dtos.participantepartida.ParticipantePartidaResponseDTO;
import io.github.devopMarkz.joga_facil.services.impl.ParticipantePartidaServiceImpl;
import io.github.devopMarkz.joga_facil.utils.ObterUsuarioLogado;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static io.github.devopMarkz.joga_facil.services.GenerateURIService.*;

@RestController
@RequestMapping("/participantes-partida")
public class ParticipantePartidaController {

    private ParticipantePartidaServiceImpl partidaService;
    private ObterUsuarioLogado obterUsuarioLogado;

    public ParticipantePartidaController(ParticipantePartidaServiceImpl partidaService, ObterUsuarioLogado obterUsuarioLogado) {
        this.partidaService = partidaService;
        this.obterUsuarioLogado = obterUsuarioLogado;
    }

    @PostMapping("/inscrever/{partidaId}")
    public ResponseEntity<ParticipantePartidaResponseDTO> inscreverParticipante(@PathVariable Long partidaId){
        ParticipantePartidaResponseDTO participantePartidaResponseDTO = partidaService.insert(partidaId);
        return ResponseEntity.created(gerarURI(obterUsuarioLogado.obterUsuario().getEmail())).body(participantePartidaResponseDTO);
    }

}
