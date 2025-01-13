//package my.pool.api.controller;
//
//import my.pool.api.service.users.models.UserDTO;
//import my.pool.api.service.users.models.UserEntity;
//import my.pool.api.repository.UserRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.DynamicPropertyRegistry;
//import org.springframework.test.context.DynamicPropertySource;
//import org.springframework.test.web.servlet.MockMvc;
//import org.testcontainers.containers.MongoDBContainer;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//import static org.hamcrest.Matchers.*;
//
//
//@SpringBootTest
//@AutoConfigureMockMvc
//class UserControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    private static final MongoDBContainer mongoDBContainer;
//
//    static {
//        mongoDBContainer = new MongoDBContainer("mongo:6.0");
//        mongoDBContainer.start();
//    }
//
//    @DynamicPropertySource
//    static void mongoProperties(DynamicPropertyRegistry registry) {
//        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
//    }
//
//    private UserEntity userEntity;
//    private UserDTO userDTO;
//
//    @BeforeEach
//    void setUp() {
//        userRepository.deleteAll();
//
//        userEntity = UserEntity.builder()
//                .id("111")
//                .name("Thales Spanhol")
//                .email("thales@example.com")
//                .password("Senh@123")
//                .build();
//
//        userDTO = new UserDTO("Novo Nome", "novonome@example.com", "Senh@456");
//    }
//
//    @Test
//    void testGetUserSuccess() throws Exception {
//        userRepository.save(userEntity); // Insere o usuário no banco
//
//        mockMvc.perform(get("/my-pool/user/111")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id", is("111")))
//                .andExpect(jsonPath("$.name", is("Thales Spanhol")))
//                .andExpect(jsonPath("$.email", is("thales@example.com")));
//    }
//
//    @Test
//    void testGetUserNotFound() throws Exception {
//        mockMvc.perform(get("/my-pool/user/222")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isNotFound());
//    }
//
//    @Test
//    void testPostUserSuccess() throws Exception {
//        mockMvc.perform(post("/my-pool/user")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(userDTO.toString()))
//                .andExpect(status().isCreated());
//
//        // Verifica se o usuário foi salvo no banco
//        UserEntity savedUser = userRepository.findByEmail("novonome@example.com").orElseThrow();
//        assertEquals("Novo Nome", savedUser.getName());
//    }
//
//    @Test
//    void testPutUserSuccess() throws Exception {
//        userRepository.save(userEntity);
//
//        UserEntity savedUser = userRepository.findById("111").orElseThrow();
//        assertEquals("Thales Spanhol", savedUser.getName());
//
//        mockMvc.perform(put("/my-pool/user/111")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(userDTO.toString()))
//                .andExpect(status().isNoContent());
//
//        // Verifica se o nome foi atualizado no banco
//        UserEntity updatedUser = userRepository.findById("111").orElseThrow();
//        assertEquals("Novo Nome", updatedUser.getName());
//    }
//
//    @Test
//    void testPutUserNotFound() throws Exception {
//        mockMvc.perform(put("/my-pool/user/222")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(userDTO.toString()))
//                .andExpect(status().isNotFound());
//    }
//
//    @Test
//    void testDeleteUserSuccess() throws Exception {
//        userRepository.save(userEntity);
//
//        UserEntity savedUser = userRepository.findByEmail("thales@example.com").orElseThrow();
//        assertEquals("Thales Spanhol", savedUser.getName());
//
//        mockMvc.perform(delete("/my-pool/user/111")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isNoContent());
//
//        // Verifica se o usuário foi removido do banco
//        assertFalse(userRepository.findById("111").isPresent());
//    }
//
//    @Test
//    void testDeleteUserNotFound() throws Exception {
//        mockMvc.perform(delete("/my-pool/user/222")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isNotFound());
//    }
//}
