package io.github.devopMarkz.joga_facil.controllers;

import io.github.devopMarkz.joga_facil.dtos.auth.AuthDTO;
import io.github.devopMarkz.joga_facil.dtos.token.TokenDTO;
import io.github.devopMarkz.joga_facil.services.TokenService;
import io.github.devopMarkz.joga_facil.services.impl.TokenServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.ZoneId;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private AuthenticationManager authenticationManager;
    private TokenService tokenService;

    public AuthenticationController(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody AuthDTO authDTO){
        var auth = new UsernamePasswordAuthenticationToken(authDTO.login(), authDTO.senha());
        authenticationManager.authenticate(auth);
        String token = tokenService.obterToken(authDTO);
        LocalDateTime expire = LocalDateTime.ofInstant(tokenService.obterTempoDeDuracao(token), ZoneId.systemDefault());
        return ResponseEntity.ok(new TokenDTO(token, expire, "Bearer"));
    }

}
