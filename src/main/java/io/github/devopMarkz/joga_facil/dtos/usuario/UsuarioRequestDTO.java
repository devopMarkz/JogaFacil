package io.github.devopMarkz.joga_facil.dtos.usuario;

import io.github.devopMarkz.joga_facil.dtos.role.RoleDTO;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record UsuarioRequestDTO(
        @NotBlank(message = "Campo NOME não pode estar nulo.")
        String nome,

        @Email(message = "Deve conter formato de E-MAIL.")
        @NotBlank(message = "Campo EMAIL não pode estar nulo.")
        String email,

        @NotBlank(message = "Campo SENHA não pode estar nulo.")
        //@Size(min = 8, max = 16)
        String senha,

        @NotBlank(message = "campo TELEFONE não pode estar nulo.")
        String telefone,

        List<RoleDTO> roles
) {
}
