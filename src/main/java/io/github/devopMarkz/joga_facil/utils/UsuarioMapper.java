package io.github.devopMarkz.joga_facil.utils;

import io.github.devopMarkz.joga_facil.exceptions.ResourceNotFoundException;
import io.github.devopMarkz.joga_facil.dtos.role.RoleDTO;
import io.github.devopMarkz.joga_facil.dtos.usuario.UsuarioRequestDTO;
import io.github.devopMarkz.joga_facil.dtos.usuario.UsuarioResponseDTO;
import io.github.devopMarkz.joga_facil.model.Usuario;
import io.github.devopMarkz.joga_facil.model.enums.RoleEnum;
import io.github.devopMarkz.joga_facil.repositories.RoleRepository;
import io.github.devopMarkz.joga_facil.repositories.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UsuarioMapper {

    private final UsuarioRepository usuarioRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioMapper(UsuarioRepository usuarioRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Usuario toEntity(UsuarioRequestDTO usuarioRequestDTO){
        Usuario usuario = new Usuario(
                usuarioRequestDTO.nome(),
                usuarioRequestDTO.email(),
                passwordEncoder.encode(usuarioRequestDTO.senha()),
                usuarioRequestDTO.telefone()
        );

        usuarioRequestDTO.roles()
                .forEach(roleDTO -> {
                    usuario.addRole(roleRepository.findByAuthority(RoleEnum.valueOf(roleDTO.authority()))
                            .orElseThrow(() -> new ResourceNotFoundException("Role inexistente.")));
                });

        return usuario;
    }

    public UsuarioResponseDTO toDTO(Usuario usuario){
        List<RoleDTO> roleDTOS = usuario.getRoles().stream().map(role -> new RoleDTO(role.getId(), role.getAuthority())).toList();
        return new UsuarioResponseDTO(usuario.getId(), usuario.getNome(), usuario.getEmail(), usuario.getTelefone(), roleDTOS);
    }
}
