package io.github.devopMarkz.joga_facil.model;

import io.github.devopMarkz.joga_facil.model.enums.StatusPagamento;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "tb_participante_partida")
public class ParticipantePartida {

    @EmbeddedId
    private ParticipantePartidaId participantePartidaId = new ParticipantePartidaId();

    @ManyToOne
    @MapsId("usuarioId")
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @MapsId("partidaId")
    @JoinColumn(name = "partida_id")
    private Partida partida;

    @Column(name = "valor_pagamento")
    private Double valorPagamento = 0.0;

    @Column(name = "status_pagamento")
    @Enumerated(EnumType.STRING)
    private StatusPagamento statusPagamento = StatusPagamento.PENDENTE; // Ex: "PENDENTE" ou "PAGO"

    @Column(name = "confirmacao_presenca")
    private Boolean confirmacaoPresenca = false;

    public ParticipantePartida() {
    }

    public ParticipantePartida(Usuario usuario, Partida partida) {
        this.usuario = usuario;
        this.partida = partida;
    }

    public ParticipantePartida(Partida partida, Boolean confirmacaoPresenca) {
        this.partida = partida;
        this.confirmacaoPresenca = confirmacaoPresenca;
    }

    public ParticipantePartida(Usuario usuario, Partida partida, Boolean confirmacaoPresenca) {
        this.usuario = usuario;
        this.partida = partida;
        this.confirmacaoPresenca = confirmacaoPresenca;
    }

    public ParticipantePartida(Usuario usuario, Partida partida, StatusPagamento statusPagamento, Boolean confirmacaoPresenca) {
        this.usuario = usuario;
        this.partida = partida;
        this.statusPagamento = statusPagamento;
        this.confirmacaoPresenca = confirmacaoPresenca;
    }

    public ParticipantePartida(ParticipantePartidaId participantePartidaId, Usuario usuario, Partida partida, Double valorPagamento, StatusPagamento statusPagamento, Boolean confirmacaoPresenca) {
        this.participantePartidaId = participantePartidaId;
        this.usuario = usuario;
        this.partida = partida;
        this.valorPagamento = valorPagamento;
        this.statusPagamento = statusPagamento;
        this.confirmacaoPresenca = confirmacaoPresenca;
    }

    public ParticipantePartidaId getParticipantePartidaId() {
        return participantePartidaId;
    }

    public void setParticipantePartidaId(ParticipantePartidaId participantePartidaId) {
        this.participantePartidaId = participantePartidaId;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Partida getPartida() {
        return partida;
    }

    public void setPartida(Partida partida) {
        this.partida = partida;
    }

    public Double getValorPagamento() {
        return valorPagamento;
    }

    public void setValorPagamento(Double valorPagamento) {
        this.valorPagamento = valorPagamento;
    }

    public StatusPagamento getStatusPagamento() {
        return statusPagamento;
    }

    public void setStatusPagamento(StatusPagamento statusPagamento) {
        this.statusPagamento = statusPagamento;
    }

    public Boolean getConfirmacaoPresenca() {
        return confirmacaoPresenca;
    }

    public void setConfirmacaoPresenca(Boolean confirmacaoPresenca) {
        this.confirmacaoPresenca = confirmacaoPresenca;
    }

    public void confirmarPagamentoEPresenca(){
        this.statusPagamento = StatusPagamento.PAGO;
        this.confirmacaoPresenca = true;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        ParticipantePartida that = (ParticipantePartida) object;
        return Objects.equals(usuario, that.usuario) && Objects.equals(partida, that.partida);
    }

    @Override
    public int hashCode() {
        return Objects.hash(usuario, partida);
    }
}