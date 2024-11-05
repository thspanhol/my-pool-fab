package my.pool.api.service;

import lombok.RequiredArgsConstructor;
import my.pool.api.model.UserDTO;
import my.pool.api.model.UserEntity;
import my.pool.api.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserEntity find(String id){
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public void create(UserDTO userDTO){
        userRepository.insert(new UserEntity(userDTO));
    }

    public void edit(String id, UserDTO userDTO){
        userRepository.findById(id)
                .map(u -> userRepository.save(userDTO.retornaUser(id)))
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public void delete(String id){
        userRepository.deleteById(id);
    }
}
