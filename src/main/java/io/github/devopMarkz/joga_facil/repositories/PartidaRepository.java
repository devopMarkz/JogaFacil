package io.github.devopMarkz.joga_facil.repositories;

import io.github.devopMarkz.joga_facil.model.Partida;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface PartidaRepository extends JpaRepository<Partida, Long> {

    @Query("""
    SELECT obj FROM Partida obj
    WHERE (:id IS NULL OR obj.id = :id)
    AND (COALESCE(:dataMinima, obj.dataHora) = obj.dataHora OR obj.dataHora >= :dataMinima)
    AND (COALESCE(:dataMaxima, obj.dataHora) = obj.dataHora OR obj.dataHora <= :dataMaxima)
    ORDER BY obj.dataHora
    """)
    Page<Partida> searchByFilters(@Param("id") Long id,
                         @Param("dataMinima") LocalDateTime dataMinima,
                         @Param("dataMaxima") LocalDateTime dataMaxima,
                         Pageable pageable
    );

}
