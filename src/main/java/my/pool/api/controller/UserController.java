package my.pool.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import my.pool.api.service.users.DeleteDecorator;
import my.pool.api.service.users.UserFacade;
import my.pool.api.service.users.UserService;
import my.pool.api.service.users.models.UserDTO;
import my.pool.api.service.users.models.UserEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/my-pool/user")
public class UserController {

    //private final UserService userService;
    private final UserFacade userFacade;
    private final DeleteDecorator deleteDecorator;


    @Operation(description = "Busca o usuário pelo id.")
    @GetMapping("/{userId}")
    public Mono<UserEntity> getUser(@PathVariable String userId) {
        return userFacade.find(userId);
    }

    @Operation(description = "Cria um novo usuário.")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Void> postUser(@Valid @RequestBody UserDTO userDTO) {
        return userFacade.create(userDTO);
    }

    @Operation(description = "Altera email, nome ou senha do usuário existente.")
    @PutMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> putUser(@PathVariable String userId, @Valid @RequestBody UserDTO userDTO) {
        return userFacade.edit(userId, userDTO);
    }

    @Operation(description = "Deleta um usuário pelo id.")
    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteUser(@PathVariable String userId) {
        return deleteDecorator.delete(userId);
    }
}
