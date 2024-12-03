package my.pool.api.service;

import my.pool.api.integration.Integration;
import my.pool.api.integration.models.Card;
import my.pool.api.repository.PoolRepository;
import my.pool.api.repository.UserRepository;
import my.pool.api.service.cards.CardsService;
import my.pool.api.service.cards.models.PoolEntity;
import my.pool.api.service.cards.models.PoolEntityDTO;
import my.pool.api.service.cards.models.PoolEntityResponse;
import my.pool.api.service.users.models.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CardsServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PoolRepository poolRepository;

    @Mock
    private Integration integration;

    @InjectMocks
    private CardsService cardsService;

    private Card card1;
    private Card card2;
    private List<String> poolCardsMongo;
    private List<Card> poolCardsResponse;
    private UserEntity userEntity;
    private PoolEntityDTO poolEntityDTO;
    private PoolEntity poolEntity;
    private PoolEntityResponse poolEntityResponse;

    @BeforeEach
    void setUp() {

        userEntity = UserEntity.builder()
                .id("111")
                .name("Thales Spanhol")
                .pools(new ArrayList<>())
                .build();

        card1 = new Card("111","card_test1","card_display_name1","card_name1","card_pitch1","card_cost1","card_defense1","card_life1","card_intellect1","card_power1","card_object_type1","card_text1","card_text_html1","card_typebox1","card_url1");
        card2 = new Card("222","card_test2","card_display_name2","card_name2","card_pitch2","card_cost2","card_defense2","card_life2","card_intellect2","card_power2","card_object_type2","card_text2","card_text_html2","card_typebox2","card_url2");

        poolCardsMongo = new ArrayList<>();
        poolCardsMongo.add(card1.getCard_id());
        poolCardsMongo.add(card2.getCard_id());

        poolCardsResponse = new ArrayList<>();
        poolCardsResponse.add(card1);
        poolCardsResponse.add(card2);

        poolEntityDTO = new PoolEntityDTO("My Test Pool", true, "111",  poolCardsMongo);

        poolEntityResponse = new PoolEntityResponse("123", "My Test Pool", true, "111",  poolCardsResponse);

        poolEntity = new PoolEntity("123", "My Test Pool", true, "111",  poolCardsMongo);
    }

    @Test
    void testFindByName() {
        String name = "testName";

        when(integration.api(name)).thenReturn(poolCardsResponse);

        assertEquals(poolCardsResponse, cardsService.findByName(name));
        verify(integration, times(1)).api(name);
    }

    @Test
    void testAddPool() {

        when(userRepository.findById("111")).thenReturn(Optional.of(userEntity));

        cardsService.addPool(poolEntityDTO);

        assertEquals(1, userEntity.getPools().size());
        verify(userRepository, times(1)).save(userEntity);
        verify(poolRepository, times(1)).insert(new PoolEntity(poolEntityDTO));
    }

    @Test
    void testFindPoolById() {

        when(poolRepository.findById("123")).thenReturn(Optional.of(poolEntity));
        when(integration.getDataPool(poolEntity.getPoolCards())).thenReturn(poolCardsResponse);

        assertEquals(poolEntityResponse, cardsService.findPoolById("123"));
        verify(poolRepository, times(1)).findById("123");
        verify(integration, times(1)).getDataPool(poolEntity.getPoolCards());
    }

    @Test
    void testDeletePool() {

        when(userRepository.findById("111")).thenReturn(Optional.of(userEntity));

        cardsService.addPool(poolEntityDTO);

        assertEquals(1, userEntity.getPools().size());
        verify(userRepository, times(1)).save(userEntity);

        when(poolRepository.findById("123")).thenReturn(Optional.of(poolEntity));

        cardsService.deletePool("123");

        assertFalse(userEntity.getPools().isEmpty());
        verify(poolRepository, times(1)).delete(poolEntity);

    }

    @Test
    void testAddCardToPool() {

        when(poolRepository.findById("123")).thenReturn(Optional.of(poolEntity));

        cardsService.addCardToPool("123", poolCardsMongo);

        assertEquals(4, poolEntity.getPoolCards().size());
        verify(poolRepository, times(1)).save(poolEntity);
    }

    @Test
    void testDeleteCardToPool() {

        when(poolRepository.findById("123")).thenReturn(Optional.of(poolEntity));

        cardsService.deleteCardToPool("123", poolCardsMongo);

        assertTrue(poolEntity.getPoolCards().isEmpty());
        verify(poolRepository, times(1)).save(poolEntity);
    }

    @Test
    void testRenamePool() {

        when(poolRepository.findById("123")).thenReturn(Optional.of(poolEntity));

        cardsService.renamePool("123", "New Name");

        assertEquals("New Name", poolEntity.getName());
        verify(poolRepository, times(1)).save(poolEntity);
    }
}