package my.pool.api.service.users;

import lombok.RequiredArgsConstructor;
import my.pool.api.repository.UserRepository;
import my.pool.api.service.users.models.UserDTO;
import my.pool.api.service.users.models.UserEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserFacade implements DeleteInterface{

    private final UserService userService;
    private final UserRepository userRepository;


    public Mono<UserEntity> find(String userId) {
        return userService.findUserById(userId);
    }

    public Mono<Void> create(UserDTO userDTO) {
        return userRepository.insert(
                new UserEntity.Builder()
                        .name(userDTO.name())
                        .email(userDTO.email())
                        .password(userDTO.password())
                        .build())
                .then();
    }

    public Mono<Void> edit(String userId, UserDTO userDTO) {
        return userService.findUserById(userId)
                .flatMap(user -> userRepository.save(userDTO.retornaUser(user)))
                .then();
    }

    @Override
    public Mono<Void> delete(String userId) {
        return userService.findUserById(userId)
                .flatMap(user -> userService.deleteAssociatedPools(user)
                        .then(userService.deleteUserById(user.getId())));
    }

}
