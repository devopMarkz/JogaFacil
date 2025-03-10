package io.github.devopMarkz.joga_facil.repositories;

import io.github.devopMarkz.joga_facil.model.ParticipantePartida;
import io.github.devopMarkz.joga_facil.model.ParticipantePartidaId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipantePartidaRepository extends JpaRepository<ParticipantePartida, ParticipantePartidaId> {
}
