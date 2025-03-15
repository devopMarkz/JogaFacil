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

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        ParticipantePartida that = (ParticipantePartida) object;
        return Objects.equals(participantePartidaId, that.participantePartidaId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(participantePartidaId);
    }
}