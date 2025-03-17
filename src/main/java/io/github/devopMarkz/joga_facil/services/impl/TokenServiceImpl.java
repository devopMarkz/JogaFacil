package io.github.devopMarkz.joga_facil.services.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import io.github.devopMarkz.joga_facil.dtos.auth.AuthDTO;
import io.github.devopMarkz.joga_facil.exceptions.ResourceNotFoundException;
import io.github.devopMarkz.joga_facil.model.Role;
import io.github.devopMarkz.joga_facil.model.Usuario;
import io.github.devopMarkz.joga_facil.repositories.UsuarioRepository;
import io.github.devopMarkz.joga_facil.services.TokenService;
import io.github.devopMarkz.joga_facil.services.exceptions.TokenExpiradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public String obterToken(AuthDTO authDTO){
        var usuario = usuarioRepository.searchByEmail(authDTO.login())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário inexistente."));
        return gerarToken(usuario);
    }

    @Override
    public String obterLogin(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256("secret");
            return JWT.require(algorithm)
                    .withIssuer("joga-facil-api")
                    .build()
                    .verify(token)
                    .getSubject();
        }catch (JWTCreationException e){
            throw new RuntimeException(e);
        }catch (TokenExpiredException e){
            throw new TokenExpiradoException("Token expirado.");
        }
    }

    @Override
    public Instant obterTempoDeDuracao(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256("secret");
            return JWT.require(algorithm)
                    .withIssuer("joga-facil-api")
                    .build()
                    .verify(token)
                    .getExpiresAtAsInstant();
        }catch (JWTCreationException e){
            throw new RuntimeException(e);
        }
    }

    private String gerarToken(Usuario usuario){
        List<String> roles = usuario.getRoles().stream().map(Role::getAuthority).toList();
        try {
            Algorithm algorithm = Algorithm.HMAC256("secret");
            return JWT.create()
                    .withIssuer("joga-facil-api")
                    .withSubject(usuario.getEmail())
                    .withClaim("roles", roles)
                    .withExpiresAt(Instant.now().plus(2L, ChronoUnit.HOURS))
                    .sign(algorithm);
        } catch (JWTCreationException e) {
            throw new RuntimeException(e);
        }
    }

}
