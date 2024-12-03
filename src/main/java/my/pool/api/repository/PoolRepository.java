package my.pool.api.repository;

import my.pool.api.service.cards.models.PoolEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PoolRepository extends MongoRepository<PoolEntity, String> {

}
