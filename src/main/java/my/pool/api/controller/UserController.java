package my.pool.api.controller;

import lombok.RequiredArgsConstructor;
import my.pool.api.model.*;
import my.pool.api.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/my-pool/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public UserEntity getUser(@PathVariable String id) {
        return userService.find(id);
    }

    @PostMapping
    public void postUser(@RequestBody UserDTO userDTO) {
        userService.create(userDTO);
    }

    @PutMapping("/{id}")
    public void putUser(@PathVariable String id, @RequestBody UserDTO userDTO) {
        userService.edit(id, userDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable String id) {
        userService.delete(id);
    }

}
