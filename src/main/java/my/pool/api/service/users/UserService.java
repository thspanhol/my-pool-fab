package my.pool.api.service.users;

import lombok.RequiredArgsConstructor;
import my.pool.api.repository.PoolRepository;
import my.pool.api.service.users.models.UserDTO;
import my.pool.api.service.users.models.UserEntity;
import my.pool.api.repository.UserRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PoolRepository poolRepository;

//    public Mono<UserEntity> find(String userId) {
//        return userRepository.findById(userId)
//                .switchIfEmpty(Mono.error(new RuntimeException("User not found.")));
//    }
//
//
//    public Mono<Void> create(UserDTO userDTO) {
//        return userRepository.insert(new UserEntity.Builder()
//                        .name(userDTO.name())
//                        .email(userDTO.email())
//                        .password(userDTO.password())
//                        .build())
//                .then();
//    }
//
//    public Mono<Void> edit(String userId, UserDTO userDTO) {
//        return userRepository.findById(userId)
//                .switchIfEmpty(Mono.error(new RuntimeException("User not found.")))
//                .flatMap(user -> userRepository.save(userDTO.retornaUser(user)))
//                .then();
//    }

//    public Mono<Void> delete(String userId) {
//        return userRepository.findById(userId)
//                .switchIfEmpty(Mono.error(new RuntimeException("User not found.")))
//                .flatMap(user -> Flux.fromIterable(user.getPools())
//                        .flatMap(poolRepository::deleteById)
//                        .then(userRepository.deleteById(user.getId())));
//    }

    public Mono<UserEntity> findUserById(String userId) {
        return userRepository.findById(userId)
                .switchIfEmpty(Mono.error(new RuntimeException("User not found.")));
    }

    public Mono<Void> deleteAssociatedPools(UserEntity user) {
        return Flux.fromIterable(user.getPools())
                .flatMap(poolRepository::deleteById)
                .then();
    }

    public Mono<Void> deleteUserById(String userId) {
        return userRepository.deleteById(userId);
    }

}
