package io.github.devopMarkz.joga_facil.repositories;

import io.github.devopMarkz.joga_facil.model.Role;
import io.github.devopMarkz.joga_facil.model.Usuario;
import io.github.devopMarkz.joga_facil.model.enums.RoleEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RoleRepository roleRepository;

    private Usuario usuario;

    @BeforeEach
    void setup(){
        usuario = new Usuario("Marcos", "marcos@gmail.com", "123", "98986063363");
        Role role1 = new Role(null, RoleEnum.ROLE_ORGANIZADOR);
        Role role2 = new Role(null, RoleEnum.ROLE_PARTICIPANTE);

        role1 = roleRepository.save(role1);
        role2 = roleRepository.save(role2);

        usuario.addRole(role1);
        usuario.addRole(role2);

        usuarioRepository.save(usuario);
    }

    @Test
    @DisplayName("Given Person Email when SearchByEmail then Return Person Object")
    void testGivenPersonId_whenFindById_thenReturnPersonObject() {
        // Arrange
        String email = "marcos@gmail.com";

        // Act
        Usuario foundedUser = usuarioRepository.searchByEmail(email).get();

        // Assert
        assertNotNull(foundedUser);
        assertNotNull(foundedUser.getRoles());
    }
}