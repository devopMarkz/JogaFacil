package io.github.devopMarkz.joga_facil.repositories;

import io.github.devopMarkz.joga_facil.model.ParticipantePartida;
import io.github.devopMarkz.joga_facil.model.ParticipantePartidaId;
import io.github.devopMarkz.joga_facil.model.Partida;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipantePartidaRepository extends JpaRepository<ParticipantePartida, ParticipantePartidaId> {

    @Query(value = "SELECT count(*) " +
            "FROM tb_participante_partida " +
            "WHERE tb_participante_partida.partida_id = :partidaId", nativeQuery = true)
    long countParticipantesByPartidaId(@Param("partidaId") Long partidaId);

    @Query(value = "SELECT tb_partida.* FROM tb_participante_partida " +
            "INNER JOIN tb_partida " +
            "ON tb_participante_partida.partida_id = tb_partida.id " +
            "WHERE tb_participante_partida.usuario_id = :usuarioId", nativeQuery = true)
    Page<Partida> searchPartidasByParticipante(@Param("usuarioId") Long usuarioId,
                                               Pageable pageable);

}
