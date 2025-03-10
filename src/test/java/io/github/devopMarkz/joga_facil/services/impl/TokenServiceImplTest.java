package io.github.devopMarkz.joga_facil.services.impl;

import io.github.devopMarkz.joga_facil.dtos.auth.AuthDTO;
import io.github.devopMarkz.joga_facil.model.Role;
import io.github.devopMarkz.joga_facil.model.Usuario;
import io.github.devopMarkz.joga_facil.model.enums.RoleEnum;
import io.github.devopMarkz.joga_facil.repositories.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class TokenServiceImplTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private TokenServiceImpl tokenService;

    private AuthDTO authDTO;

    private Usuario usuario;

    private String token;

    @BeforeEach
    void setup(){
        authDTO = new AuthDTO("marcos@gmail.com", "123");
        usuario = new Usuario(1L, "Marcos", "marcos@gmail.com", "123", "98986063363");
        usuario.addRole(new Role(1, RoleEnum.ROLE_ORGANIZADOR));
        token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJqb2dhLWZhY2lsLWFwaSIsInN1YiI6Im1hcmNvc0BnbWFpbC5jb20iLCJyb2xlcyI6WyJST0xFX09SR0FOSVpBRE9SIl0sImV4cCI6MTc0MTYyMzQyMH0.f80EpP2om1y45EivyjILN7nXjAoX0fI4f-_4sfmPDBs";
    }

    @Test
    @DisplayName("Given user login When obterToken Then return token JWT")
    void testObterToken() {
        // Arrange
        given(usuarioRepository.searchByEmail(authDTO.login())).willReturn(Optional.of(usuario));

        // Act
        String token = tokenService.obterToken(authDTO);

        System.out.println(token);

        // Assert
        assertNotNull(token);
    }

    @Test
    @DisplayName("Given token When obterLogin Then return user login")
    void testObterLogin() {
        // Act
        String login = tokenService.obterLogin(token);

        System.out.println(login);

        // Assert
        assertNotNull(login);
        assertEquals(authDTO.login(), login);
    }
}