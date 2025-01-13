package my.pool.api.service.users.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

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
//    public UserEntity retornaUser(UserEntity userEntity) {
//        return UserEntity.builder()
//                .id(userEntity.getId())
//                .name(this.name())
//                .email(this.email())
//                .password(this.password())
//                .pools(userEntity.getPools())
//                .build();
//    }

    public UserEntity retornaUser(UserEntity userEntity) {
        return new UserEntity.Builder()
                .id(userEntity.getId())
                .name(this.name())
                .email(this.email())
                .password(this.password())
                .pools(userEntity.getPools())
                .build();
    }

    @Override
    public String toString() {

        return String.format("""
        {
            "name": "%s",
            "email": "%s",
            "password": "%s"
        }
        """, name, email, password);
    }
}
