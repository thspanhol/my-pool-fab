//package my.pool.api.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import my.pool.api.config.AppConfig;
//import my.pool.api.integration.models.Card;
//import my.pool.api.repository.PoolRepository;
//import my.pool.api.repository.UserRepository;
//import my.pool.api.service.cards.CardsService;
//import my.pool.api.service.cards.models.PoolEntity;
//import my.pool.api.service.cards.models.PoolEntityDTO;
//import my.pool.api.service.users.models.UserEntity;
//import org.hamcrest.Matchers;
//import org.junit.jupiter.api.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.DynamicPropertyRegistry;
//import org.springframework.test.context.DynamicPropertySource;
//import org.springframework.test.context.TestPropertySource;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//import org.testcontainers.containers.MongoDBContainer;
//
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.hamcrest.Matchers.hasSize;
//import static org.junit.jupiter.api.Assertions.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//class CardsControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private CardsService cardsService;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private PoolRepository poolRepository;
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
//    private Card card1;
//    private Card card2;
//    private PoolEntityDTO poolEntityDTO;
//    private List<String> poolCardsMongo;
//    private List<Card> poolCardsResponse;
//    private UserEntity userEntity;
//
//    @BeforeEach
//    void setUp() {
//        userRepository.deleteAll();
//        poolRepository.deleteAll();
//
//        userEntity = UserEntity.builder()
//                .id("111")
//                .name("Thales Spanhol")
//                .pools(new ArrayList<>())
//                .build();
//
//        card1 = new Card("become-the-arknight-3","card_test1","card_display_name1","card_name1","card_pitch1","card_cost1","card_defense1","card_life1","card_intellect1","card_power1","card_object_type1","card_text1","card_text_html1","card_typebox1","card_url1");
//        card2 = new Card("sonata-fantasmia-3","card_test2","card_display_name2","card_name2","card_pitch2","card_cost2","card_defense2","card_life2","card_intellect2","card_power2","card_object_type2","card_text2","card_text_html2","card_typebox2","card_url2");
//
//        poolCardsMongo = new ArrayList<>();
//        poolCardsMongo.add(card1.getCard_id());
//        poolCardsMongo.add(card2.getCard_id());
//
//        poolCardsResponse = new ArrayList<>();
//        poolCardsResponse.add(card1);
//        poolCardsResponse.add(card2);
//
//        poolEntityDTO = new PoolEntityDTO("My Test Pool", true, "111",  poolCardsMongo);
//    }
//
//    @Test
//    void testGetPoolSuccess() throws Exception {
//
//        userRepository.save(userEntity);
//        cardsService.addPool(poolEntityDTO);
//
//        PoolEntity response = poolRepository.findAll().getFirst();
//
//        mockMvc.perform(get("/my-pool/cards/" + response.getId())
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(response.getId())))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("My Test Pool")))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.poolCards", hasSize(2)));
//    }
//
//    @Test
//    void testGetPoolNotFound() throws Exception {
//        mockMvc.perform(get("/my-pool/cards/invalidPool")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isNotFound());
//    }
//
//    @Test
//    void testGetCardsByNameSuccess() throws Exception {
//
//        mockMvc.perform(get("/my-pool/cards/search")
//                        .param("name", "Chane, Bound by Shadow")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1)))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", Matchers.is("Chane, Bound by Shadow")));
//    }
//
//    @Test
//    void testAddPoolSuccess() throws Exception {
//        userRepository.save(userEntity);
//        cardsService.addPool(poolEntityDTO);
//
//        PoolEntity responseBefore = poolRepository.findAll().getFirst();
//
//        mockMvc.perform(put("/my-pool/cards/addCardsToPool")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .param("poolId", responseBefore.getId())
//                        .content(new ObjectMapper().writeValueAsString(poolCardsMongo)))
//                .andExpect(status().isNoContent());
//
//        PoolEntity responseAfter = poolRepository.findAll().getFirst();
//
//        assertEquals("become-the-arknight-3", responseAfter.getPoolCards().get(2));
//    }
//
//    @Test
//    void testAddCardsToPoolSuccess() throws Exception {
//
//        userRepository.save(userEntity);
//        cardsService.addPool(poolEntityDTO);
//
//        PoolEntity responseBefore = poolRepository.findAll().getFirst();
//
//        mockMvc.perform(put("/my-pool/cards/addCardsToPool")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .param("poolId", responseBefore.getId())
//                        .content(new ObjectMapper().writeValueAsString(poolCardsMongo)))
//                .andExpect(status().isNoContent());
//
//        PoolEntity responseAfter = poolRepository.findAll().getFirst();
//
//        assertEquals("become-the-arknight-3", responseAfter.getPoolCards().get(2));
//    }
//
//    @Test
//    void testDeleteCardsToPoolSuccess() throws Exception {
//
//        userRepository.save(userEntity);
//        cardsService.addPool(poolEntityDTO);
//
//        PoolEntity responseBefore = poolRepository.findAll().getFirst();
//
//        mockMvc.perform(put("/my-pool/cards/deleteCardsToPool")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .param("poolId", responseBefore.getId())
//                        .content(new ObjectMapper().writeValueAsString(poolCardsMongo)))
//                .andExpect(status().isNoContent());
//
//        PoolEntity responseAfter = poolRepository.findAll().getFirst();
//
//        assertEquals(0, responseAfter.getPoolCards().size());
//    }
//
//    @Test
//    void testRenamePoolSuccess() throws Exception {
//
//        userRepository.save(userEntity);
//        cardsService.addPool(poolEntityDTO);
//
//        PoolEntity responseBefore = poolRepository.findAll().getFirst();
//
//        mockMvc.perform(put("/my-pool/cards/renamePool")
//                        .param("poolId", responseBefore.getId())
//                        .param("rename", "Updated Pool Name")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isNoContent());
//
//        PoolEntity responseAfter = poolRepository.findAll().getFirst();
//
//        assertEquals("Updated Pool Name", responseAfter.getName());
//    }
//
//    @Test
//    void testDeletePoolSuccess() throws Exception {
//
//        userRepository.save(userEntity);
//        cardsService.addPool(poolEntityDTO);
//
//        PoolEntity responseBefore = poolRepository.findAll().getFirst();
//
//        mockMvc.perform(delete("/my-pool/cards/deletePool")
//                        .param("poolId", responseBefore.getId())
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isNoContent());
//
//        List<PoolEntity> responseAfter = poolRepository.findAll();
//
//        assertTrue(responseAfter.isEmpty());
//    }
//
//    @Test
//    void testDeletePoolNotFound() throws Exception {
//        mockMvc.perform(delete("/my-pool/cards/deletePool")
//                        .param("poolId", "invalidPool")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isNotFound());
//    }
//}
