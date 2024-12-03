//package my.pool.api.model;
//
//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.NotEmpty;
//import jakarta.validation.constraints.Pattern;
//
//import java.util.List;
//
//public record PoolDTO(
//        @NotBlank(message = "Necessário informar o nome.")
//        @Pattern(regexp = "^[A-Z][a-z]*(?: [A-Z][a-z]*){0,14}$", message = "Os nomes devem começar com letras maiusculas e conter entre 4-30 caracteres.")
//        String name,
//        @NotEmpty(message = "Necessário informar as cartas da pool.")
//        List<Card> poolCards
//) {
//
//}
