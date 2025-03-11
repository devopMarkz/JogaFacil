package io.github.devopMarkz.joga_facil.dtos.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UsuarioUpdateSenhaDTO(
        @Email(message = "Deve conter formato de E-MAIL.")
        @NotBlank(message = "Campo EMAIL não pode estar nulo.")
        String email,

        @NotBlank(message = "Campo SENHA não pode estar nulo.")
        String senha
) {
}
