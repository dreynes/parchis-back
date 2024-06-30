package miw.tfm.parchis.repositories;

import miw.tfm.parchis.models.GameState;
import miw.tfm.parchis.mongo.dto.GameStateEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface GameStateRepository extends MongoRepository<GameStateEntity, String> {
    List<GameStateEntity> findByUser_Username(String username);
    List<GameStateEntity> findByGameName(String gameName);
    Optional<GameStateEntity> findByGameNameAndUser_Username(String gameName, String username);
}
