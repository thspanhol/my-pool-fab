package my.pool.api.repository;

import my.pool.api.service.users.models.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import java.util.Optional;

public interface UserRepository extends ReactiveMongoRepository<UserEntity, String> {

    //Optional<UserEntity> findByEmail(String mail);
}
