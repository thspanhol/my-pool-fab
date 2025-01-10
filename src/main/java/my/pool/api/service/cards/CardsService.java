package my.pool.api.service.cards;

import lombok.RequiredArgsConstructor;
import my.pool.api.integration.models.Card;
import my.pool.api.integration.Integration;
import my.pool.api.repository.PoolRepository;
import my.pool.api.repository.UserRepository;
import my.pool.api.service.cards.models.PoolEntity;
import my.pool.api.service.cards.models.PoolEntityDTO;
import my.pool.api.service.cards.models.PoolEntityResponse;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CardsService {

    private final UserRepository userRepository;
    private final PoolRepository poolRepository;
    private final Integration integration;

    public Flux<Card> getAll() {
        return integration.fullApi();
    }

    public Flux<Card> findByName(String name) {
        return integration.api(name);
    }

    public Mono<Void> addPool(PoolEntityDTO poolEntityDTO) {
        return userRepository.findById(poolEntityDTO.creatorId())
                .switchIfEmpty(Mono.error(new RuntimeException("User not found.")))
                .flatMap(creator -> {

                    PoolEntity newPool = PoolEntity.builder()
                            .name(poolEntityDTO.name())
                            .isPublic(poolEntityDTO.isPublic())
                            .creatorId(poolEntityDTO.creatorId())
                            .poolCards(poolEntityDTO.poolCards())
                            .build();

                    return poolRepository.insert(newPool)
                            .then(Mono.just(creator))
                            .flatMap(updatedCreator -> {
                                updatedCreator.getPools().add(newPool.getId());
                                return userRepository.save(updatedCreator);
                            });
                })
                .then();
    }

    public Mono<PoolEntityResponse> findPoolById(String poolId) {
        return poolRepository.findById(poolId)
                .switchIfEmpty(Mono.error(new RuntimeException("Pool not found.")))
                .flatMap(poolEntity -> integration.getDataPool(poolEntity.getPoolCards())
                        .collectList()
                        .map(cards -> PoolEntityResponse.toResponse(poolEntity, cards)));
    }

    public Mono<Void> deletePool(String poolId) {
        return poolRepository.findById(poolId)
                .switchIfEmpty(Mono.error(new RuntimeException("Pool not found.")))
                .flatMap(result -> userRepository.findById(result.getCreatorId())
                        .switchIfEmpty(Mono.error(new RuntimeException("User not found.")))
                        .flatMap(creator -> {
                            creator.getPools().remove(poolId);
                            return userRepository.save(creator)
                                    .then(poolRepository.delete(result));
                        }));
    }

    public Mono<Void> addCardToPool(String poolId, List<String> list) {
        return poolRepository.findById(poolId)
                .switchIfEmpty(Mono.error(new RuntimeException("Pool not found.")))
                .flatMap(result -> {
                    result.getPoolCards().addAll(list);
                    return poolRepository.save(result);
                })
                .then();
    }

    public Mono<Void> deleteCardToPool(String poolId, List<String> list) {
        return poolRepository.findById(poolId)
                .switchIfEmpty(Mono.error(new RuntimeException("Pool not found.")))
                .flatMap(result -> {
                    List<String> newPoolCards = new ArrayList<>(result.getPoolCards());
                    list.forEach(newPoolCards::remove);
                    result.setPoolCards(newPoolCards);
                    return poolRepository.save(result);
                })
                .then();
    }

    public Mono<Void> renamePool(String poolId, String rename) {
        return poolRepository.findById(poolId)
                .switchIfEmpty(Mono.error(new RuntimeException("Pool not found.")))
                .flatMap(result -> {
                    result.setName(rename);
                    return poolRepository.save(result);
                })
                .then();
    }
}
