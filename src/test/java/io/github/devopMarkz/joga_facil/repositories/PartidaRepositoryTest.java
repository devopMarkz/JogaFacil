package io.github.devopMarkz.joga_facil.repositories;

import io.github.devopMarkz.joga_facil.model.Partida;
import io.github.devopMarkz.joga_facil.model.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PartidaRepositoryTest {

    @Autowired
    private PartidaRepository partidaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private Partida partida;

    private Usuario usuario;

    @BeforeEach
    void setup(){
        usuario = new Usuario("Marcos", "marcos@gmail.com", "123", "98986765435");
        partida = new Partida(LocalDateTime.now(), LocalDateTime.now().plusHours(2), "Parque Timbiras", 200.0, 18, usuario);
        usuario = usuarioRepository.save(usuario);
        partida = partidaRepository.save(partida);
    }

    @Test
    @DisplayName("Given Filters When searchByFilters Then return Page<Partidas>")
    void testSearchByFilters() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        LocalDateTime dataMinima = LocalDateTime.now().minusHours(1);
        LocalDateTime dataMaxima = LocalDateTime.of(2025, 06, 13, 15, 00);

        // Act
        Page<Partida> partidaEncontrada = partidaRepository.searchByFilters(1L, dataMinima, dataMaxima, pageable);
        Partida otherPartida = partidaEncontrada.getContent().getFirst();

        // Assert
        assertNotNull(partidaEncontrada);
        assertEquals("Parque Timbiras", otherPartida.getLocal());
    }
}