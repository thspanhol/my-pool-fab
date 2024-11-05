package my.pool.api.repository;

import lombok.RequiredArgsConstructor;
import my.pool.api.model.UserEntity;
import my.pool.api.model.Pool;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class FindPoolByIdRepository implements CustomUserRepository {

    private final MongoTemplate mongoTemplate;

    @Override
    public Pool findPoolByPoolId(String poolId) {

        Query query = new Query(Criteria.where("pools._id").is(poolId));
        query.fields().include("pools.$");

        UserEntity user = mongoTemplate.findOne(query, UserEntity.class);
        return (user != null && user.getPools() != null && !user.getPools().isEmpty()) ? user.getPools().get(0) : null;
    }
}

