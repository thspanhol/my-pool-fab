package my.pool.api.service;

import my.pool.api.service.users.UserService;
import my.pool.api.service.users.models.UserDTO;
import my.pool.api.service.users.models.UserEntity;
import my.pool.api.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private UserEntity userEntity;
    private UserDTO userDTO;

    @BeforeEach
    void setUp() {

        userEntity = UserEntity.builder()
                .id("111")
                .name("Thales Spanhol")
                .email("thales@example.com")
                .password("Senh@123")
                .build();

        userDTO = new UserDTO("Thales Spanhol", "thales@example.com", "Senh@123");
    }

    @Test
    void testFindUserExists() {

        when(userRepository.findById("111")).thenReturn(Optional.of(userEntity));

        UserEntity result = userService.find("111");

        assertNotNull(result);
        assertEquals("111", result.getId());
        assertEquals("Thales Spanhol", result.getName());
        assertEquals("thales@example.com", result.getEmail());
        assertEquals("Senh@123", result.getPassword());
        verify(userRepository, times(1)).findById("111");
    }

    @Test
    void testFindUserNotFound() {

        when(userRepository.findById("111")).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> userService.find("111"));

        assertEquals("User not found.", exception.getMessage());
        verify(userRepository, times(1)).findById("111");
    }

    @Test
    void testCreate() {

        when(userRepository.insert(any(UserEntity.class))).thenReturn(userEntity);

        userService.create(userDTO);

        verify(userRepository, times(1)).insert(any(UserEntity.class));
    }

    @Test
    void testEditUserExists() {

        when(userRepository.findById("111")).thenReturn(Optional.of(userEntity));
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);

        userService.edit("111", userDTO);

        verify(userRepository, times(1)).findById("111");
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    void testEditUserNotFound() {

        when(userRepository.findById("111")).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> userService.edit("111", userDTO));

        assertEquals("User not found.", exception.getMessage());
        verify(userRepository, times(1)).findById("111");
        verify(userRepository, times(0)).save(any(UserEntity.class));
    }

    @Test
    void testDelete() {
        when(userRepository.findById("111")).thenReturn(Optional.of(userEntity));

        userService.delete("111");
        verify(userRepository, times(1)).deleteById("111");
    }
}