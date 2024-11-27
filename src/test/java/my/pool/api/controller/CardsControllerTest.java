package my.pool.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import my.pool.api.model.*;
import my.pool.api.repository.UserRepository;
import my.pool.api.service.CardsService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest // Inicializa todo o contexto do Spring
@AutoConfigureMockMvc // Configura o MockMvc automaticamente
@ActiveProfiles("test") // Usa o perfil de teste configurado para o MongoDB Embedded
class CardsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CardsService cardsService;

    @Autowired
    private UserRepository userRepository;

    private Card card1;
    private Card card2;
    private PoolDTO poolDTO;
    private List<Card> cardList;
    private UserEntity userEntity;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();

        userEntity = UserEntity.builder()
                .id("111")
                .name("Thales Spanhol")
                .pools(new ArrayList<>())
                .build();

        card1 = new Card("111","card_test1","card_display_name1","card_name1","card_pitch1","card_cost1","card_defense1","card_life1","card_intellect1","card_power1","card_object_type1","card_text1","card_text_html1","card_typebox1","card_url1");
        card2 = new Card("222","card_test2","card_display_name2","card_name2","card_pitch2","card_cost2","card_defense2","card_life2","card_intellect2","card_power2","card_object_type2","card_text2","card_text_html2","card_typebox2","card_url2");

        cardList = new ArrayList<>();
        cardList.add(card1);
        cardList.add(card2);

        poolDTO = new PoolDTO("My Test Pool", cardList);
    }

    @Test
    void testGetPoolSuccess() throws Exception {
        userRepository.save(userEntity);

        cardsService.addPool("111", poolDTO);

        UserEntity findPooldUser = userRepository.findById("111").orElseThrow();

        mockMvc.perform(get("/my-pool/cards/" + findPooldUser.getPools().getFirst().getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(findPooldUser.getPools().getFirst().getId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("My Test Pool")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.poolCards", hasSize(2)));
    }

    @Test
    void testGetPoolNotFound() throws Exception {
        mockMvc.perform(get("/my-pool/cards/invalidPool")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetCardsByNameSuccess() throws Exception {

        mockMvc.perform(get("/my-pool/cards/search")
                        .param("name", "Chane, Bound by Shadow")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", Matchers.is("Chane, Bound by Shadow")));
    }

    @Test
    void testAddPoolSuccess() throws Exception {
        userRepository.save(userEntity);

        mockMvc.perform(put("/my-pool/cards/111")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(poolDTO)))
                .andExpect(status().isNoContent());

        UserEntity findPooldUser = userRepository.findById("111").orElseThrow();
        assertEquals("My Test Pool", findPooldUser.getPools().getFirst().getName());
    }

    @Test
    void testAddCardsToPoolSuccess() throws Exception {

        userRepository.save(userEntity);

        cardsService.addPool("111", poolDTO);

        UserEntity findUserAfterAdd = userRepository.findById("111").orElseThrow();

        mockMvc.perform(put("/my-pool/cards/addCardsToPool")
                        .param("userId", "111")
                        .param("poolId", findUserAfterAdd.getPools().getFirst().getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(cardList)))
                .andExpect(status().isNoContent());

        UserEntity findUserBeforeAdd = userRepository.findById("111").orElseThrow();
        assertEquals(4, findUserBeforeAdd.getPools().getFirst().getPoolCards().size());
    }

    @Test
    void testDeleteCardsToPoolSuccess() throws Exception {

        userRepository.save(userEntity);

        cardsService.addPool("111", poolDTO);

        UserEntity findUserAfterAdd = userRepository.findById("111").orElseThrow();

        mockMvc.perform(put("/my-pool/cards/deleteCardsToPool")
                        .param("userId", "111")
                        .param("poolId", findUserAfterAdd.getPools().getFirst().getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(cardList)))
                .andExpect(status().isNoContent());

        UserEntity findUserBeforeAdd = userRepository.findById("111").orElseThrow();
        assertEquals(0, findUserBeforeAdd.getPools().getFirst().getPoolCards().size());
    }

    @Test
    void testRenamePoolSuccess() throws Exception {

        userRepository.save(userEntity);

        cardsService.addPool("111", poolDTO);

        UserEntity findUserAfter = userRepository.findById("111").orElseThrow();

        mockMvc.perform(put("/my-pool/cards/renamePool")
                        .param("userId", "111")
                        .param("poolId", findUserAfter.getPools().getFirst().getId())
                        .param("rename", "Updated Pool Name")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        UserEntity findUserBefore = userRepository.findById("111").orElseThrow();
        assertEquals("Updated Pool Name", findUserBefore.getPools().getFirst().getName());

    }

    @Test
    void testDeletePoolSuccess() throws Exception {

        userRepository.save(userEntity);

        cardsService.addPool("111", poolDTO);

        UserEntity findUserAfter = userRepository.findById("111").orElseThrow();

        mockMvc.perform(delete("/my-pool/cards/deletePool")
                        .param("userId", "111")
                        .param("poolId", findUserAfter.getPools().getFirst().getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        UserEntity findUserBefore = userRepository.findById("111").orElseThrow();

        assertTrue(findUserBefore.getPools().isEmpty());
    }

    @Test
    void testDeletePoolNotFound() throws Exception {
        mockMvc.perform(delete("/my-pool/cards/deletePool")
                        .param("userId", "111")
                        .param("poolId", "invalidPool")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
