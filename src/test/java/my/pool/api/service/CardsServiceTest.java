package my.pool.api.service;

import my.pool.api.model.*;
import my.pool.api.repository.FindPoolByIdRepository;
import my.pool.api.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class CardsServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FindPoolByIdRepository findPoolByIdRepository;

    @Autowired
    private CardsService cardsService;

    @Autowired
    private RestTemplate restTemplate; // Use uma configuração mock ou bean real

    private UserEntity userEntity;
    private Pool pool;
    private PoolDTO poolDTO;
    private List<Card> cardList;

    @BeforeEach
    void setUp() {
        // Limpa o banco antes de cada teste
        userRepository.deleteAll();

        // Configuração inicial dos dados
        userEntity = UserEntity.builder()
                .id("111")
                .name("Thales Spanhol")
                .pools(new ArrayList<>())
                .build();

        cardList = new ArrayList<>();

        Card card = new Card("abc","card_test","card_display_name","card_name","card_pitch","card_cost","card_defense","card_life","card_intellect","card_power","card_object_type","card_text","card_text_html","card_typebox","card_url");

        cardList.add(card);

        poolDTO = new PoolDTO("My Pool", cardList);
        pool = new Pool("222","My Pool", cardList);


    }

//    @Test
//    void testGetAllCards() {
//        // Simular a chamada de API usando um RestTemplate Mock
//        String url = "https://cards.fabtcg.com/api/search/v1/cards";
//        CardsResponse response = new CardsResponse();
//        response.setResults(cardList);
//        response.setNext(null);
//
//        when(restTemplate.getForObject(url, CardsResponse.class)).thenReturn(response);
//
//        List<Card> result = cardsService.getAll();
//
//        assertEquals(2, result.size());
//    }

    @Test
    void testAddPool() {
        userRepository.save(userEntity); // Persistindo o usuário no MongoDB

        cardsService.addPool("111", poolDTO);

        UserEntity updatedUser = userRepository.findById("111").orElseThrow();
        assertEquals(1, updatedUser.getPools().size());
        assertEquals("My Pool", updatedUser.getPools().getFirst().getName());
    }

    @Test
    void testFindPoolById() {
        userRepository.save(userEntity); // Persistindo o usuário no MongoDB

        cardsService.addPool("111", poolDTO);

        UserEntity findPooldUser = userRepository.findById("111").orElseThrow();

        Pool result = cardsService.findPoolById(findPooldUser.getPools().getFirst().getId());

        assertNotNull(result);
        assertEquals("My Pool", result.getName());
    }

    @Test
    void testDeletePool() {
        userRepository.save(userEntity); // Persistindo o usuário no MongoDB

        cardsService.addPool("111", poolDTO);

        UserEntity deletePooldUser = userRepository.findById("111").orElseThrow();

        cardsService.deletePool("111", deletePooldUser.getPools().getFirst().getId());

        UserEntity result = userRepository.findById("111").orElseThrow();

        assertTrue(result.getPools().isEmpty());
    }

    @Test
    void testAddCardToPool() {
        userRepository.save(userEntity); // Persistindo o usuário no MongoDB

        cardsService.addPool("111", poolDTO);

        UserEntity addCardPoolUser = userRepository.findById("111").orElseThrow();

        cardsService.addCardToPool("111", addCardPoolUser.getPools().getFirst().getId(), cardList);

        UserEntity result = userRepository.findById("111").orElseThrow();

        assertEquals(2, result.getPools().getFirst().getPoolCards().size());
    }

    @Test
    void testRenamePool() {
        userRepository.save(userEntity); // Persistindo o usuário no MongoDB

        cardsService.addPool("111", poolDTO);

        UserEntity renamePoolUser = userRepository.findById("111").orElseThrow();

        assertEquals("My Pool", renamePoolUser.getPools().getFirst().getName());

        cardsService.renamePool("111", renamePoolUser.getPools().getFirst().getId(), "Renamed Pool");

        UserEntity result = userRepository.findById("111").orElseThrow();

        assertEquals("Renamed Pool", result.getPools().getFirst().getName());
    }

}