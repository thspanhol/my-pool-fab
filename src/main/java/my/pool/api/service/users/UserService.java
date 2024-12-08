package my.pool.api.service.users;

import lombok.RequiredArgsConstructor;
import my.pool.api.service.users.models.UserDTO;
import my.pool.api.service.users.models.UserEntity;
import my.pool.api.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserEntity find(String userId){
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found."));
    }

    public void create(UserDTO userDTO){
        userRepository.insert(new UserEntity(userDTO));
    }

    public void edit(String userId, UserDTO userDTO){
        userRepository.findById(userId)
                .map(u -> userRepository.save(userDTO.retornaUser(u)))
                .orElseThrow(() -> new RuntimeException("User not found."));
    }

    public void delete(String userId){
        userRepository.findById(userId)
                .ifPresentOrElse(
                        u -> userRepository.deleteById(u.getId()),
                        () -> { throw new RuntimeException("User not found."); }
                );
    }
}
