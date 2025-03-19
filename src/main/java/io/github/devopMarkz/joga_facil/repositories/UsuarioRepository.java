package io.github.devopMarkz.joga_facil.repositories;

import io.github.devopMarkz.joga_facil.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query("SELECT obj FROM Usuario obj JOIN FETCH obj.roles WHERE obj.email = :email")
    Optional<Usuario> searchByEmail(@Param("email") String email);

    @Query("SELECT obj FROM Usuario obj JOIN FETCH obj.roles WHERE obj.id = :id")
    Optional<Usuario> searchByIdWithRoles(@Param("id") Long id);

    boolean existsByEmail(@Param("email") String email);

    Usuario getReferenceByEmail(@Param("email") String email);
}
