package my.pool.api.service.cards;

import lombok.RequiredArgsConstructor;
import my.pool.api.integration.models.Card;
import my.pool.api.integration.Integration;
import my.pool.api.repository.PoolRepository;
import my.pool.api.repository.UserRepository;
import my.pool.api.service.cards.models.PoolEntity;
import my.pool.api.service.cards.models.PoolEntityDTO;
import my.pool.api.service.cards.models.PoolEntityResponse;
import my.pool.api.service.users.models.UserEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CardsService {

    private final UserRepository userRepository;
    private final PoolRepository poolRepository;
    private final Integration integration;

    public List<Card> getAll(){
        return integration.api("");
    }

    public List<Card> findByName(String name){
        return integration.api(name);
    }

    public void addPool(PoolEntityDTO poolEntityDTO){

        UserEntity creator = userRepository.findById(poolEntityDTO.creatorId())
                .orElseThrow(() -> new RuntimeException("User not found."));

        PoolEntity newPool = new PoolEntity(poolEntityDTO);

        poolRepository.insert(newPool);

        creator.getPools().add(newPool.getId());

        userRepository.save(creator);
    }

    public PoolEntityResponse findPoolById(String poolId){
        PoolEntity result = poolRepository.findById(poolId)
                .orElseThrow(() -> new RuntimeException("Pool not found."));

        return new PoolEntityResponse(result, integration);
    }

    public void deletePool(String poolId){

        PoolEntity result = poolRepository.findById(poolId)
                .orElseThrow(() -> new RuntimeException("Pool not found."));

        UserEntity creator = userRepository.findById(result.getCreatorId())
                .orElseThrow(() -> new RuntimeException("User not found."));

        creator.getPools().remove(poolId);

        userRepository.save(creator);
        poolRepository.delete(result);
    }

    public void addCardToPool(String poolId, List<String> list) {

        PoolEntity result = poolRepository.findById(poolId)
                .orElseThrow(() -> new RuntimeException("Pool not found."));

//        List<String> newPoolCards = new ArrayList<>(list);
//        newPoolCards.addAll(poolToAdd.getPoolCards());
//        poolToAdd.setPoolCards(newPoolCards);

        result.getPoolCards().addAll(list);
        poolRepository.save(result);
    }

    public void deleteCardToPool(String poolId, List<String> list) {

        PoolEntity result = poolRepository.findById(poolId)
                .orElseThrow(() -> new RuntimeException("Pool not found."));

        list.forEach(card -> result.getPoolCards().remove(card));
        poolRepository.save(result);
    }

    public void renamePool(String poolId, String  rename) {

        PoolEntity result = poolRepository.findById(poolId)
                .orElseThrow(() -> new RuntimeException("Pool not found."));

        result.setName(rename);

        poolRepository.save(result);
    }

}
