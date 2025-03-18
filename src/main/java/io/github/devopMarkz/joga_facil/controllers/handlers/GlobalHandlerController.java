package io.github.devopMarkz.joga_facil.controllers.handlers;

import io.github.devopMarkz.joga_facil.exceptions.ResourceNotFoundException;
import io.github.devopMarkz.joga_facil.dtos.erro.ErroResponseDTO;
import io.github.devopMarkz.joga_facil.exceptions.SenhaIncorretaException;
import io.github.devopMarkz.joga_facil.exceptions.UsuarioJaExistenteException;
import io.github.devopMarkz.joga_facil.services.exceptions.LimiteDeParticipantesAtingidoException;
import io.github.devopMarkz.joga_facil.services.exceptions.OrganizadorInvalidoException;
import io.github.devopMarkz.joga_facil.services.exceptions.TokenExpiradoException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalHandlerController {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErroResponseDTO> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request){
        HttpStatus status = HttpStatus.NOT_FOUND;
        List<String> errors = List.of(e.getMessage());
        ErroResponseDTO erroResponseDTO = new ErroResponseDTO(Instant.now(), status.value(), request.getRequestURI(), errors);
        return ResponseEntity.status(status).body(erroResponseDTO);
    }

    @ExceptionHandler(UsuarioJaExistenteException.class)
    public ResponseEntity<ErroResponseDTO> usuarioJaExistente(UsuarioJaExistenteException e, HttpServletRequest request){
        HttpStatus status = HttpStatus.CONFLICT;
        List<String> errors = List.of(e.getMessage());
        ErroResponseDTO erroResponseDTO = new ErroResponseDTO(Instant.now(), status.value(), request.getRequestURI(), errors);
        return ResponseEntity.status(status).body(erroResponseDTO);
    }

    @ExceptionHandler(SenhaIncorretaException.class)
    public ResponseEntity<ErroResponseDTO> senhaIncorreta(SenhaIncorretaException e, HttpServletRequest request){
        HttpStatus status = HttpStatus.BAD_REQUEST;
        List<String> errors = List.of(e.getMessage());
        ErroResponseDTO erroResponseDTO = new ErroResponseDTO(Instant.now(), status.value(), request.getRequestURI(), errors);
        return ResponseEntity.status(status).body(erroResponseDTO);
    }

    @ExceptionHandler(LimiteDeParticipantesAtingidoException.class)
    public ResponseEntity<ErroResponseDTO> limiteDeParticipantesAtingido(LimiteDeParticipantesAtingidoException e, HttpServletRequest request){
        HttpStatus status = HttpStatus.BAD_REQUEST;
        List<String> errors = List.of(e.getMessage());
        ErroResponseDTO erroResponseDTO = new ErroResponseDTO(Instant.now(), status.value(), request.getRequestURI(), errors);
        return ResponseEntity.status(status).body(erroResponseDTO);
    }

    @ExceptionHandler(OrganizadorInvalidoException.class)
    public ResponseEntity<ErroResponseDTO> organizadorInvalidoException(OrganizadorInvalidoException e, HttpServletRequest request){
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        List<String> errors = List.of(e.getMessage());
        ErroResponseDTO erroResponseDTO = new ErroResponseDTO(Instant.now(), status.value(), request.getRequestURI(), errors);
        return ResponseEntity.status(status).body(erroResponseDTO);
    }

    @ExceptionHandler(TokenExpiradoException.class)
    public ResponseEntity<ErroResponseDTO> tokenExpiradoException(TokenExpiradoException e, HttpServletRequest request){
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        List<String> errors = List.of(e.getMessage());
        ErroResponseDTO erroResponseDTO = new ErroResponseDTO(Instant.now(), status.value(), request.getRequestURI(), errors);
        return ResponseEntity.status(status).body(erroResponseDTO);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErroResponseDTO> methodArgumentTypeMismatch(MethodArgumentTypeMismatchException e, HttpServletRequest request){
        HttpStatus status = HttpStatus.BAD_REQUEST;
        List<String> errors = List.of("Entrada de dados incorreta.");
        ErroResponseDTO erroResponseDTO = new ErroResponseDTO(Instant.now(), status.value(), request.getRequestURI(), errors);
        return ResponseEntity.status(status).body(erroResponseDTO);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErroResponseDTO> httpMessageNotReadableException(HttpMessageNotReadableException e, HttpServletRequest request){
        HttpStatus status = HttpStatus.BAD_REQUEST;
        List<String> errors = List.of("Corpo da requisição inválido.");
        ErroResponseDTO erroResponseDTO = new ErroResponseDTO(Instant.now(), status.value(), request.getRequestURI(), errors);
        return ResponseEntity.status(status).body(erroResponseDTO);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroResponseDTO> methodArgumentNotValid(MethodArgumentNotValidException e, HttpServletRequest request){
        HttpStatus status = HttpStatus.BAD_REQUEST;

        List<String> errors = new ArrayList<>();

        for (FieldError x : e.getFieldErrors()){
            errors.add(x.getDefaultMessage());
        }

        ErroResponseDTO erroResponseDTO = new ErroResponseDTO(Instant.now(), status.value(), request.getRequestURI(), errors);

        return ResponseEntity.status(status).body(erroResponseDTO);
    }
}
