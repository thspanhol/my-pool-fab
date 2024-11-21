package my.pool.api.repository;

import my.pool.api.model.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<UserEntity, String> {

    Optional<UserEntity> findByName(String name);

    Optional<UserEntity> findByEmail(String mail);
}
