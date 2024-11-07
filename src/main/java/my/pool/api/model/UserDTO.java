package my.pool.api.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Email;

public record UserDTO(
        @NotBlank(message = "Necessário informar o nome.")
        @Pattern(regexp = "^[A-Z][a-z]*(?: [A-Z][a-z]*){0,14}$", message = "Os nomes devem começar com letras maiusculas e conter entre 4-30 caracteres.")
        String name,
        @NotBlank(message = "Necessário informar o email.")
        @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", message = "O email deve ser válido.")
        String email,
        @NotBlank(message = "Necessário informar uma senha.")
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*(),.?\":{}|<>])[A-Za-z\\d!@#$%^&*(),.?\":{}|<>]{8,}$", message = "A senha deve ter entre 8-12 dígitos, conter letra minúscula, maiúscula e pelo menos um número.")
        String password
) {
    public UserEntity retornaUser(String id) {
        return UserEntity.builder()
                .id(id)
                .name(this.name())
                .email(this.email())
                .password(this.password())
                .build();
    }
}
