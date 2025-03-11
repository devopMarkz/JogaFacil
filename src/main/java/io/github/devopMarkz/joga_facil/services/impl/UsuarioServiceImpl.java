package io.github.devopMarkz.joga_facil.services.impl;

import io.github.devopMarkz.joga_facil.dtos.usuario.UsuarioRequestDTO;
import io.github.devopMarkz.joga_facil.dtos.usuario.UsuarioResponseDTO;
import io.github.devopMarkz.joga_facil.exceptions.UsuarioJaExistenteException;
import io.github.devopMarkz.joga_facil.model.Usuario;
import io.github.devopMarkz.joga_facil.repositories.RoleRepository;
import io.github.devopMarkz.joga_facil.repositories.UsuarioRepository;
import io.github.devopMarkz.joga_facil.services.UsuarioService;
import io.github.devopMarkz.joga_facil.utils.UsuarioMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RoleRepository roleRepository;
    private final UsuarioMapper usuarioMapper;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, RoleRepository roleRepository, UsuarioMapper usuarioMapper) {
        this.usuarioRepository = usuarioRepository;
        this.roleRepository = roleRepository;
        this.usuarioMapper = usuarioMapper;
    }

    @Transactional
    @Override
    public UsuarioResponseDTO insert(UsuarioRequestDTO usuarioRequestDTO){
        verificarExistenciaDoEmail(usuarioRequestDTO.email());
        Usuario usuario = usuarioMapper.toEntity(usuarioRequestDTO);
        Usuario usuarioSalvo = usuarioRepository.save(usuario);
        return usuarioMapper.toDTO(usuarioSalvo);
    }

    private void verificarExistenciaDoEmail(String email){
        if(usuarioRepository.existsByEmail(email)){
            throw new UsuarioJaExistenteException("Já existe usuário com este e-mail.");
        }
    }

}
