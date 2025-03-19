package io.github.devopMarkz.joga_facil.controllers;

import io.github.devopMarkz.joga_facil.dtos.participantepartida.ParticipantePartidaResponseDTO;
import io.github.devopMarkz.joga_facil.services.impl.ParticipantePartidaServiceImpl;
import io.github.devopMarkz.joga_facil.utils.ObterUsuarioLogado;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static io.github.devopMarkz.joga_facil.services.GenerateURIService.*;

@RestController
@RequestMapping("/participantes-partida")
@Tag(
        name = "Operações de Participante da Partida",
        description = "Endpoints para realizar inscrições em partidas, deleção de participantes e confirmação de pagamento e presença."
)
public class ParticipantePartidaController {

    private final ParticipantePartidaServiceImpl partidaService;
    private final ObterUsuarioLogado obterUsuarioLogado;

    public ParticipantePartidaController(ParticipantePartidaServiceImpl partidaService, ObterUsuarioLogado obterUsuarioLogado) {
        this.partidaService = partidaService;
        this.obterUsuarioLogado = obterUsuarioLogado;
    }

    @PostMapping("/{partidaId}/inscrever/{codigoPartida}")
    @Operation(summary = "Inscrição de usuário", description = "Endpoint de inscrição de usuário em partida")
    public ResponseEntity<ParticipantePartidaResponseDTO> inscreverParticipante(@PathVariable("partidaId") Long partidaId,
                                                                                @PathVariable("codigoPartida") String codigoPartida){
        ParticipantePartidaResponseDTO participantePartidaResponseDTO = partidaService.insert(partidaId, codigoPartida);
        return ResponseEntity.created(gerarURI(obterUsuarioLogado.obterUsuario().getEmail())).body(participantePartidaResponseDTO);
    }

    @DeleteMapping("/{participanteEmail}/{partidaId}")
    @PreAuthorize("hasRole('ROLE_ORGANIZADOR')")
    @Operation(summary = "Deleta participante", description = "Endpoint de deleção de participante de partida específica")
    public ResponseEntity<Void> deletarParticipante(@PathVariable("participanteEmail") String participanteEmail, @PathVariable("partidaId") Long partidaId){
        partidaService.deleteParticipanteByPartidaId(participanteEmail, partidaId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{partidaId}/participantes/{participanteEmail}/confirmar-pagamento")
    @PreAuthorize("hasRole('ROLE_ORGANIZADOR')")
    @Operation(summary = "Atualiza status de participante", description = "Endpoint de atualização de participante de partida específica")
    public ResponseEntity<Void> atualizarPagamentoDeParticipante(@PathVariable("partidaId") Long partidaId,
                                                                 @PathVariable("participanteEmail") String participanteEmail){
        partidaService.updatePagamentoParcicipante(partidaId, participanteEmail);
        return ResponseEntity.noContent().build();
    }

}
