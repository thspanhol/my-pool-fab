package my.pool.api.controller;

import my.pool.api.model.UserDTO;
import my.pool.api.model.UserEntity;
import my.pool.api.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private UserEntity userEntity;
    private UserDTO userDTO;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        userEntity = UserEntity.builder()
                .id("123")
                .name("John Doe")
                .email("john.doe@example.com")
                .password("Senh@123")
                .build();

        userDTO = new UserDTO("John Doe", "john.doe@example.com", "Senh@123");
    }

    @Test
    void testGetUserSuccess() throws Exception {
        when(userService.find("123")).thenReturn(userEntity);

        mockMvc.perform(get("/my-pool/user/123")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is("123")))
                .andExpect(jsonPath("$.name", is("John Doe")))
                .andExpect(jsonPath("$.email", is("john.doe@example.com")));

        verify(userService, times(1)).find("123");
    }

    @Test
    void testGetUserNotFound() throws Exception {
        when(userService.find("123")).thenThrow(new RuntimeException("User not found."));

        mockMvc.perform(get("/my-pool/user/123")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(userService, times(1)).find("123");
    }

    @Test
    void testPostUserSuccess() throws Exception {
        mockMvc.perform(post("/my-pool/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isCreated());

        verify(userService, times(1)).create(any(UserDTO.class));
    }

    @Test
    void testPutUserSuccess() throws Exception {
        mockMvc.perform(put("/my-pool/user/123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isNoContent());

        verify(userService, times(1)).edit(eq("123"), any(UserDTO.class));
    }

    @Test
    void testPutUserNotFound() throws Exception {
        doThrow(new RuntimeException("User not found.")).when(userService).edit(eq("123"), any(UserDTO.class));

        mockMvc.perform(put("/my-pool/user/123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isNotFound());

        verify(userService, times(1)).edit(eq("123"), any(UserDTO.class));
    }

    @Test
    void testDeleteUserSuccess() throws Exception {
        mockMvc.perform(delete("/my-pool/user/123")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(userService, times(1)).delete("123");
    }

    @Test
    void testDeleteUserNotFound() throws Exception {
        doThrow(new RuntimeException("User not found.")).when(userService).delete("123");

        mockMvc.perform(delete("/my-pool/user/123")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(userService, times(1)).delete("123");
    }
}
