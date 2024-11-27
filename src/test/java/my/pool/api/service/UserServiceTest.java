package my.pool.api.service;

import my.pool.api.model.UserDTO;
import my.pool.api.model.UserEntity;
import my.pool.api.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest // Inicializa o contexto Spring para usar MongoDB Embedded
class UserServiceTest {

    @Autowired
    private UserRepository userRepository; // Instância real conectada ao banco embedded

    @Autowired
    private UserService userService; // Classe de serviço com dependência injetada

    private UserEntity userEntity;
    private UserDTO userDTO;

    @BeforeEach
    void setUp() {
        // Limpa os dados do banco antes de cada teste
        userRepository.deleteAll();

        userEntity = UserEntity.builder()
                .id("111")
                .name("Thales Spanhol")
                .email("thales@example.com")
                .password("Senh@123")
                .build();

        userDTO = new UserDTO("Novo Nome", "thales@example.com", "Senh@123");
    }

    @Test
    void testFindUserExists() {
        // Insere o usuário no banco antes de realizar o teste
        userRepository.save(userEntity);

        UserEntity result = userService.find("111");

        assertNotNull(result);
        assertEquals("111", result.getId());
        assertEquals("Thales Spanhol", result.getName());
        assertEquals("thales@example.com", result.getEmail());
        assertEquals("Senh@123", result.getPassword());
    }

    @Test
    void testFindUserNotFound() {
        // Nenhum usuário é inserido no banco
        Exception exception = assertThrows(RuntimeException.class, () -> userService.find("111"));
        assertEquals("User not found.", exception.getMessage());
    }

    @Test
    void testCreate() {
        userService.create(userDTO);

        // Verifica se o usuário foi realmente persistido no banco
        Optional<UserEntity> savedUser = userRepository.findByName("Novo Nome");
        assertTrue(savedUser.isPresent());
        assertEquals("Novo Nome", savedUser.get().getName());
    }

    @Test
    void testEditUserExists() {
        userRepository.save(userEntity);

        userService.edit("111", userDTO);

        UserEntity updatedUser = userRepository.findById("111").orElseThrow();
        assertEquals("Novo Nome", updatedUser.getName());
    }

    @Test
    void testEditUserNotFound() {
        Exception exception = assertThrows(RuntimeException.class, () -> userService.edit("111", userDTO));
        assertEquals("User not found.", exception.getMessage());
    }

    @Test
    void testDelete() {
        userRepository.save(userEntity);

        userService.delete("111");

        Optional<UserEntity> deletedUser = userRepository.findById("111");
        assertFalse(deletedUser.isPresent());
    }
}
