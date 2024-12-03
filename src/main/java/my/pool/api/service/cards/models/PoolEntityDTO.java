package my.pool.api.service.cards.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.List;

public record PoolEntityDTO(
        @NotBlank(message = "Necessário informar o nome.")
        @Pattern(regexp = "^[A-Z][a-z]*(?: [A-Z][a-z]*){0,14}$", message = "Os nomes devem começar com letras maiusculas e conter entre 4-30 caracteres.")
        String name,
        @NotNull(message = "Necessário informar se a pool é pública ou privada.")
        Boolean isPublic,
        @NotBlank(message = "Necessário informar o id do criador.")
        String creatorId,
        @NotEmpty(message = "Necessário informar as cartas da pool.")
        List<String> poolCards
) {

}
