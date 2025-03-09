package io.github.devopMarkz.joga_facil.repositories;

import io.github.devopMarkz.joga_facil.model.Role;
import io.github.devopMarkz.joga_facil.model.enums.RoleEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    @BeforeEach
    void setup(){
        roleRepository.save(new Role(null, RoleEnum.ROLE_ORGANIZADOR));
    }

    @Test
    @DisplayName("Given Authority when FindByAuthority then Return Role Object")
    void testGivenAuthority_whenFindByAuthority_thenReturnRoleObject() {
        // Arrange
        String authority = "ROLE_ORGANIZADOR";

        // Act
        Role role = roleRepository.findByAuthority(RoleEnum.valueOf(authority)).orElse(null);

        // Act
        assertNotNull(role);
    }
}