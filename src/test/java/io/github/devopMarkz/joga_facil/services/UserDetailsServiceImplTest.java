package io.github.devopMarkz.joga_facil.services;

import io.github.devopMarkz.joga_facil.model.Usuario;
import io.github.devopMarkz.joga_facil.repositories.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImplTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    private Usuario usuario;

    @BeforeEach
    void setup(){
        usuario = new Usuario(
                "Marcos",
                "marcos@gmail.com",
                "123",
                "98986063363"
        );
    }

    @Test
    @DisplayName("Given username when loadUserByUsername then Return Usuario Object")
    void testGivenUsername_whenLoadUserByUsername_thenReturnUsuarioObject() {
        // Arrange
        String email = "marcos@gmail.com";
        given(usuarioRepository.searchByEmail(email)).willReturn(Optional.of(usuario));

        // Act
        Usuario foundedUserDetails = (Usuario) userDetailsService.loadUserByUsername(email);

        // Assert
        assertNotNull(foundedUserDetails, () -> "Resultado nulo.");
        assertEquals(email, foundedUserDetails.getEmail(), () -> "Emails divergentes.");
    }

    @Test
    @DisplayName("Given inexistent username when loadUserByUsername then Throw UsernameNotFoundException")
    void testGivenInexistentUsername_whenLoadUserByUsername_thenThrowUsernameNotFoundException() {
        // Arrange
        given(usuarioRepository.searchByEmail(anyString())).willReturn(Optional.empty());

        // Act / Assert
        assertThrows(UsernameNotFoundException.class, () -> {
            userDetailsService.loadUserByUsername("inexistent@gmail.com");
        });
    }
}