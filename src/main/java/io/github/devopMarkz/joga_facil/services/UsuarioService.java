package io.github.devopMarkz.joga_facil.services;

import io.github.devopMarkz.joga_facil.dtos.usuario.UsuarioRequestDTO;
import io.github.devopMarkz.joga_facil.dtos.usuario.UsuarioResponseDTO;

public interface UsuarioService {

    UsuarioResponseDTO insert(UsuarioRequestDTO usuarioRequestDTO);

}
