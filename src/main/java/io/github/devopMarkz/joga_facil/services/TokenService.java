package io.github.devopMarkz.joga_facil.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import io.github.devopMarkz.joga_facil.dtos.auth.AuthDTO;
import io.github.devopMarkz.joga_facil.exceptions.ResourceNotFoundException;

import java.time.Instant;

public interface TokenService {

    String obterToken(AuthDTO authDTO);

    String obterLogin(String token);

    Instant obterTempoDeDuracao(String token);

}
