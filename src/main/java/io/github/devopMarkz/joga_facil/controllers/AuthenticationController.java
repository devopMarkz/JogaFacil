package io.github.devopMarkz.joga_facil.controllers;

import io.github.devopMarkz.joga_facil.dtos.auth.AuthDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public String login(@RequestBody AuthDTO authDTO){
        var auth = new UsernamePasswordAuthenticationToken(authDTO.login(), authDTO.senha());
        authenticationManager.authenticate(auth);
        return "Ok";
    }

}
