package io.github.devopMarkz.joga_facil.dtos.usuario;

import io.github.devopMarkz.joga_facil.dtos.role.RoleDTO;

import java.util.List;

public record UsuarioResponseDTO(
        Long id,
        String nome,
        String email,
        String senha,
        String telefone,
        List<RoleDTO> roles
) {
}
