package my.pool.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import my.pool.api.model.*;
import my.pool.api.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/my-pool/user")
public class UserController {

    private final UserService userService;

    @Operation(description = "Busca o usuário pelo id.")
    @GetMapping("/{userId}")
    public UserEntity getUser(@PathVariable String userId) {
        return userService.find(userId);
    }

    @Operation(description = "Cria um novo usuário.")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void postUser(@Valid @RequestBody UserDTO userDTO) {
        userService.create(userDTO);
    }

    @Operation(description = "Altera email, nome ou senha do usuário existente.")
    @PutMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void putUser(@PathVariable String userId, @Valid @RequestBody UserDTO userDTO) {
        userService.edit(userId, userDTO);
    }

    @Operation(description = "Deleta um usuário pelo id.")
    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable String userId) {
        userService.delete(userId);
    }

}
