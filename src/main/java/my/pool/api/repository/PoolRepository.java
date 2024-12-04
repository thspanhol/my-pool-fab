package my.pool.api.repository;

import my.pool.api.service.cards.models.PoolEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PoolRepository extends MongoRepository<PoolEntity, String> {

    Optional<PoolEntity> findByName(String name);
}
