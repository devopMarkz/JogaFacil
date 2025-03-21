package io.github.devopMarkz.joga_facil.controllers;

import io.github.devopMarkz.joga_facil.dtos.auth.AuthDTO;
import io.github.devopMarkz.joga_facil.dtos.token.TokenDTO;
import io.github.devopMarkz.joga_facil.services.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;

@RestController
@RequestMapping("/auth")
@Tag(
        name = "Autenticação de Usuário",
        description = "Autentica usuário para obter token JWT"
)
public class AuthenticationController {

    private AuthenticationManager authenticationManager;
    private TokenService tokenService;

    public AuthenticationController(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    @Operation(summary = "Endpoint de Login", description = "Passa usuário e senha para obter token JWT")
    public ResponseEntity<TokenDTO> login(@RequestBody AuthDTO authDTO){
        var auth = new UsernamePasswordAuthenticationToken(authDTO.login(), authDTO.senha());
        authenticationManager.authenticate(auth);
        String token = tokenService.obterToken(authDTO);
        LocalDateTime expire = LocalDateTime.ofInstant(tokenService.obterTempoDeDuracao(token), ZoneId.systemDefault());
        return ResponseEntity.ok(new TokenDTO(token, expire, "Bearer"));
    }

}
