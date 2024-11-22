package my.pool.api.service;

import lombok.RequiredArgsConstructor;
import my.pool.api.integration.Integration;
import my.pool.api.model.*;
import my.pool.api.repository.FindPoolByIdRepository;
import my.pool.api.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CardsService {

    private final UserRepository userRepository;
    private final FindPoolByIdRepository findPoolByIdRepository;
    private final RestTemplate restTemplate;
    private final Integration integration;

    public List<Card> getAll(){
        return integration.api("");
    }

    public List<Card> findByName(String name){
        return integration.api(name);
    }

    public void addPool(String userId, PoolDTO poolDTO){
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found."));
        user.getPools().add(new Pool(poolDTO));
        userRepository.save(user);
    }

    public Pool findPoolById(String poolId){
        return findPoolByIdRepository.findPoolByPoolId(poolId);
    }

    public void deletePool(String userId, String poolId){
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found."));
        Pool removed = findPoolByIdRepository.findPoolByPoolId(poolId);
        user.getPools().remove(removed);
        userRepository.save(user);
    }

    public void addCardToPool(String userId, String poolId, List<Card> list) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found."));

        Pool poolToAdd = user.getPools().stream()
                .filter(pool -> pool.getId().equals(poolId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Pool not found."));

        list.forEach(card -> poolToAdd.getPoolCards().add(card));

        userRepository.save(user);
    }

    public void deleteCardToPool(String userId, String poolId, List<Card> list) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found."));

        Pool poolToRemoveCards = user.getPools().stream()
                .filter(pool -> pool.getId().equals(poolId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Pool not found."));

        list.forEach(card -> poolToRemoveCards.getPoolCards().remove((card)));

        userRepository.save(user);
    }

    public void renamePool(String userId, String poolId, String  rename) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found."));

        Pool poolToRename = user.getPools().stream()
                .filter(pool -> pool.getId().equals(poolId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Pool not found."));

        poolToRename.setName(rename);

        userRepository.save(user);
    }

}
