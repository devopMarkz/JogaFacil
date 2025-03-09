package io.github.devopMarkz.joga_facil.controllers.handlers;

import io.github.devopMarkz.joga_facil.exceptions.ResourceNotFoundException;
import io.github.devopMarkz.joga_facil.dtos.erro.ErroResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;
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

}
