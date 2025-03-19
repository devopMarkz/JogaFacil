package io.github.devopMarkz.joga_facil.services;

import io.github.devopMarkz.joga_facil.dtos.usuario.UsuarioRequestDTO;
import io.github.devopMarkz.joga_facil.dtos.usuario.UsuarioResponseDTO;
import io.github.devopMarkz.joga_facil.dtos.usuario.UsuarioUpdateSenhaDTO;

public interface UsuarioService {

    UsuarioResponseDTO insert(UsuarioRequestDTO usuarioRequestDTO);

    void updateSenha(UsuarioUpdateSenhaDTO usuarioUpdateSenhaDTO);

    UsuarioResponseDTO findById(Long id);

    }
