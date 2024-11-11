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
    @GetMapping("/{id}")
    public UserEntity getUser(@PathVariable String id) {
        return userService.find(id);
    }

    @Operation(description = "Cria um novo usuário.")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void postUser(@Valid @RequestBody UserDTO userDTO) {
        userService.create(userDTO);
    }

    @Operation(description = "Altera email, nome ou senha do usuário existente.")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void putUser(@PathVariable String id, @Valid @RequestBody UserDTO userDTO) {
        userService.edit(id, userDTO);
    }

    @Operation(description = "Deleta um usuário pelo id.")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable String id) {
        userService.delete(id);
    }

}
