package my.pool.api.repository;

import my.pool.api.service.cards.models.PoolEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import java.util.Optional;

public interface PoolRepository extends ReactiveMongoRepository<PoolEntity, String> {

    //Optional<PoolEntity> findByName(String name);
}
