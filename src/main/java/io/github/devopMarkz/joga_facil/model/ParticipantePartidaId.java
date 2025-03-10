package io.github.devopMarkz.joga_facil.model;

import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class ParticipantePartidaId {

    private Long usuarioId;
    private Long partidaId;

    public ParticipantePartidaId() {
    }

    public ParticipantePartidaId(Long usuarioId, Long partidaId) {
        this.usuarioId = usuarioId;
        this.partidaId = partidaId;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Long getPartidaId() {
        return partidaId;
    }

    public void setPartidaId(Long partidaId) {
        this.partidaId = partidaId;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        ParticipantePartidaId that = (ParticipantePartidaId) object;
        return Objects.equals(usuarioId, that.usuarioId) && Objects.equals(partidaId, that.partidaId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(usuarioId, partidaId);
    }
}
