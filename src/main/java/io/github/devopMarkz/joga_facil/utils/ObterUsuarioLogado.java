package io.github.devopMarkz.joga_facil.utils;

import io.github.devopMarkz.joga_facil.model.Usuario;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class ObterUsuarioLogado {

    public Usuario obterUsuario(){
        return (Usuario) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
    }

}
