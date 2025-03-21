package io.github.devopMarkz.joga_facil.repositories;

import io.github.devopMarkz.joga_facil.model.Partida;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface PartidaRepository extends JpaRepository<Partida, Long> {

    @Query("""
    SELECT obj FROM Partida obj
    WHERE (:id IS NULL OR obj.id = :id)
    AND (COALESCE(:dataMinima, obj.dataHoraInicio) = obj.dataHoraInicio OR obj.dataHoraInicio >= :dataMinima)
    AND (COALESCE(:dataMaxima, obj.dataHoraInicio) = obj.dataHoraInicio OR obj.dataHoraInicio <= :dataMaxima)
    AND NOT EXISTS (
        SELECT 1 FROM ParticipantePartida pp WHERE pp.partida.id = obj.id AND pp.usuario.id = :usuarioId
    )
    ORDER BY obj.dataHoraInicio
    """)
    Page<Partida> searchByFilters(@Param("id") Long id,
                                  @Param("dataMinima") LocalDateTime dataMinima,
                                  @Param("dataMaxima") LocalDateTime dataMaxima,
                                  @Param("usuarioId") Long usuarioId,
                                  Pageable pageable);

    @Query("SELECT p FROM Partida p LEFT JOIN FETCH p.participantes WHERE p.id = :partidaId")
    Optional<Partida> searchPartidaByIdWithParticipantes(@Param("partidaId") Long partidaId);

}
