package miw.tfm.parchis.mongo.repositories;

import miw.tfm.parchis.mongo.dto.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<UserEntity, String> {
    UserEntity findByUsername(String username);

    void deleteByUsername(String testuserRepo);
}
