package my.pool.api.service.users;

import lombok.RequiredArgsConstructor;
import my.pool.api.service.users.models.UserDTO;
import my.pool.api.service.users.models.UserEntity;
import my.pool.api.repository.UserRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

      public Mono<UserEntity> find(String userId) {
          return userRepository.findById(userId)
                  .switchIfEmpty(Mono.error(new RuntimeException("User not found.")));
      }

    public Mono<Void> create(UserDTO userDTO) {
        return userRepository.insert(new UserEntity(userDTO))
                .then(); // Retorna um Mono<Void> após a inserção
    }

    public Mono<Void> edit(String userId, UserDTO userDTO) {
        return userRepository.findById(userId)
                .flatMap(user -> userRepository.save(userDTO.retornaUser(user)))
                .then(); // Retorna um Mono<Void> após a atualização
    }

    public Mono<Void> delete(String userId) {
        return userRepository.findById(userId)
                .switchIfEmpty(Mono.error(new RuntimeException("User not found.")))
                .flatMap(user -> userRepository.deleteById(user.getId()));
    }
}
