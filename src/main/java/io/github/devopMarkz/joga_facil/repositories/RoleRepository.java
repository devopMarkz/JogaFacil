package io.github.devopMarkz.joga_facil.repositories;

import io.github.devopMarkz.joga_facil.model.Role;
import io.github.devopMarkz.joga_facil.model.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findByAuthority(RoleEnum authority);

}
