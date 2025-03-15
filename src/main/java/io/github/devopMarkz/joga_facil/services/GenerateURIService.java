package io.github.devopMarkz.joga_facil.services;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

public interface GenerateURIService {

    static <T> URI gerarURI(T id){
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }

    static <T, W> URI gerarURIDeChaveComposta(T email, W partidaId){
        return ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{email}/{partidaId}")
                .buildAndExpand(email, partidaId)
                .toUri();
    }

}
