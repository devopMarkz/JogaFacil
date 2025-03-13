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

    @Query("SELECT obj FROM Partida obj WHERE 1 = 1 " +
            "AND (:id iS NULL OR obj.id = :id) " +
            "AND (:dataMinima IS NULL or obj.dataHora >= :dataMinima) " +
            "AND (:dataMaxima IS NULL or obj.dataHora <= :dataMaxima)")
    Page<Partida> searchByFilters(@Param("id") Long id,
                         @Param("dataMinima") LocalDateTime dataMinima,
                         @Param("dataMaxima") LocalDateTime dataMaxima,
                         Pageable pageable
    );

}
