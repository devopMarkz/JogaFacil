package io.github.devopMarkz.joga_facil.model;

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
    private String statusPagamento; // Ex: "PENDENTE" ou "PAGO"

    @Column(name = "confirmacao_presenca")
    private Boolean confirmacaoPresenca;

    public ParticipantePartida() {
    }

    public ParticipantePartida(Usuario usuario, Partida partida, String statusPagamento, Boolean confirmacaoPresenca) {
        participantePartidaId.setUsuarioId(usuario.getId());
        participantePartidaId.setPartidaId(partida.getId());
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

    public String getStatusPagamento() {
        return statusPagamento;
    }

    public void setStatusPagamento(String statusPagamento) {
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