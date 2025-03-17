package io.github.devopMarkz.joga_facil.configs;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.devopMarkz.joga_facil.dtos.erro.ErroResponseDTO;
import io.github.devopMarkz.joga_facil.exceptions.ResourceNotFoundException;
import io.github.devopMarkz.joga_facil.model.Usuario;
import io.github.devopMarkz.joga_facil.repositories.UsuarioRepository;
import io.github.devopMarkz.joga_facil.services.TokenService;
import io.github.devopMarkz.joga_facil.services.exceptions.TokenExpiradoException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Instant;
import java.util.List;

@Component
public class CustomAuthenticationFilter extends OncePerRequestFilter {

    private UsuarioRepository usuarioRepository;
    private TokenService tokenService;
    private ObjectMapper objectMapper;

    public CustomAuthenticationFilter(UsuarioRepository usuarioRepository, TokenService tokenService, ObjectMapper objectMapper) {
        this.usuarioRepository = usuarioRepository;
        this.tokenService = tokenService;
        this.objectMapper = objectMapper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = extrairTokenDoHeader(request);

        if(token != null){
            try {
                String login = tokenService.obterLogin(token);

                Usuario usuario = usuarioRepository.searchByEmail(login).orElseThrow(() -> new ResourceNotFoundException("Usuário inexistente."));

                var authenticationToken = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }catch (TokenExpiradoException e){
                ErroResponseDTO erroResponseDTO = new ErroResponseDTO(
                        Instant.now(),
                        401,
                        request.getRequestURI(),
                        List.of(e.getMessage())
                );

                response.setStatus(401);
                response.setContentType("application/json");
                response.getWriter().write(objectMapper.writeValueAsString(erroResponseDTO));
                response.getWriter().flush();
            }
        }
        filterChain.doFilter(request, response);
    }

    private String extrairTokenDoHeader(HttpServletRequest request){
        var authHeader = request.getHeader("Authorization");

        if(authHeader == null){
            return null;
        }

        if(!authHeader.split(" ")[0].equals("Bearer")){
            return null;
        }

        return authHeader.split(" ")[1];
    }
}
