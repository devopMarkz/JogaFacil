package io.github.devopMarkz.joga_facil.services.impl;

import io.github.devopMarkz.joga_facil.dtos.usuario.UsuarioRequestDTO;
import io.github.devopMarkz.joga_facil.dtos.usuario.UsuarioResponseDTO;
import io.github.devopMarkz.joga_facil.dtos.usuario.UsuarioUpdateSenhaDTO;
import io.github.devopMarkz.joga_facil.exceptions.ResourceNotFoundException;
import io.github.devopMarkz.joga_facil.exceptions.SenhaIncorretaException;
import io.github.devopMarkz.joga_facil.exceptions.UsuarioJaExistenteException;
import io.github.devopMarkz.joga_facil.model.Usuario;
import io.github.devopMarkz.joga_facil.repositories.RoleRepository;
import io.github.devopMarkz.joga_facil.repositories.UsuarioRepository;
import io.github.devopMarkz.joga_facil.services.UsuarioService;
import io.github.devopMarkz.joga_facil.utils.UsuarioMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RoleRepository roleRepository;
    private final UsuarioMapper usuarioMapper;
    private PasswordEncoder passwordEncoder;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, RoleRepository roleRepository, UsuarioMapper usuarioMapper, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.roleRepository = roleRepository;
        this.usuarioMapper = usuarioMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @Override
    public UsuarioResponseDTO insert(UsuarioRequestDTO usuarioRequestDTO){
        if(verificarExistenciaDoEmail(usuarioRequestDTO.email())){
            throw new UsuarioJaExistenteException("Já existe usuário com este e-mail.");
        }
        Usuario usuario = usuarioMapper.toEntity(usuarioRequestDTO);
        Usuario usuarioSalvo = usuarioRepository.save(usuario);
        return usuarioMapper.toDTO(usuarioSalvo);
    }

    @Transactional
    @Override
    public void updateSenha(UsuarioUpdateSenhaDTO usuarioUpdateSenhaDTO){
        Usuario usuario = usuarioRepository.searchByEmail(usuarioUpdateSenhaDTO.email())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado."));

        String senhaEncodada = usuario.getPassword();
        String senha = usuarioUpdateSenhaDTO.senha();

        if(passwordEncoder.matches(senha, senhaEncodada)){
            String novaSenha = passwordEncoder.encode(usuarioUpdateSenhaDTO.novaSenha());
            usuario.setSenha(novaSenha);
        } else{
            throw new SenhaIncorretaException("Senha incorreta.");
        }
    }

    private boolean verificarExistenciaDoEmail(String email){
        return usuarioRepository.existsByEmail(email);
    }

}
