package my.pool.api.service;

import my.pool.api.integration.Integration;
import my.pool.api.model.*;
import my.pool.api.repository.FindPoolByIdRepository;
import my.pool.api.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CardsServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private FindPoolByIdRepository findPoolByIdRepository;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private Integration integration;

    @InjectMocks
    private CardsService cardsService;

    private Card card1;
    private Card card2;
    private List<Card> cardList;
    private UserEntity userEntity;
    private PoolDTO poolDTO;
    private Pool pool;

    @BeforeEach
    void setUp() {

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

        pool = new Pool("444", "My Test Pool", cardList);

        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindByName() {
        String name = "testName";

        when(integration.api(name)).thenReturn(cardList);

        assertEquals(cardList, cardsService.findByName(name));
        verify(integration, times(1)).api(name);
    }

    @Test
    void testAddPool() {
        String userId = "111";

        when(userRepository.findById("111")).thenReturn(Optional.of(userEntity));

        cardsService.addPool(userId, poolDTO);

        assertEquals(1, userEntity.getPools().size());
        verify(userRepository, times(1)).save(userEntity);
    }

    @Test
    void testFindPoolById() {
        String poolId = "444";

        when(findPoolByIdRepository.findPoolByPoolId(poolId)).thenReturn(pool);

        assertEquals(pool, cardsService.findPoolById(poolId));
        verify(findPoolByIdRepository, times(1)).findPoolByPoolId(poolId);
    }

    @Test
    void testDeletePool() {
        String userId = "111";
        String poolId = "444";

        List<Pool> poolList = new ArrayList<>();
        poolList.add(pool);

        userEntity.setPools(poolList);

        assertFalse(userEntity.getPools().isEmpty());
        assertEquals(1, userEntity.getPools().size());

        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));
        when(findPoolByIdRepository.findPoolByPoolId(poolId)).thenReturn(pool);

        cardsService.deletePool(userId, poolId);

        assertTrue(userEntity.getPools().isEmpty());
        verify(userRepository, times(1)).save(userEntity);
    }

    @Test
    void testAddCardToPool() {
        String userId = "111";
        String poolId = "444";

        List<Pool> poolList = new ArrayList<>();
        poolList.add(pool);

        userEntity.setPools(poolList);

        assertEquals(1, userEntity.getPools().size());
        assertEquals(2, userEntity.getPools().getFirst().getPoolCards().size());

        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));

        cardsService.addCardToPool(userId, poolId, cardList);

        assertEquals(1, userEntity.getPools().size());
        assertEquals(4, userEntity.getPools().getFirst().getPoolCards().size());
        verify(userRepository, times(1)).save(userEntity);
    }

    @Test
    void testDeleteCardToPool() {
        String userId = "111";
        String poolId = "444";

        List<Pool> poolList = new ArrayList<>();
        poolList.add(pool);

        userEntity.setPools(poolList);

        assertEquals(1, userEntity.getPools().size());
        assertEquals(2, userEntity.getPools().getFirst().getPoolCards().size());

        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));

        cardsService.deleteCardToPool(userId, poolId, cardList);

        assertTrue(pool.getPoolCards().isEmpty());
        verify(userRepository, times(1)).save(userEntity);
    }

    @Test
    void testRenamePool() {
        String userId = "111";
        String poolId = "444";
        String newName = "newPoolName";

        List<Pool> poolList = new ArrayList<>();
        poolList.add(pool);

        userEntity.setPools(poolList);

        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));

        cardsService.renamePool(userId, poolId, newName);

        assertEquals(newName, userEntity.getPools().getFirst().getName());
        verify(userRepository, times(1)).save(userEntity);
    }
}