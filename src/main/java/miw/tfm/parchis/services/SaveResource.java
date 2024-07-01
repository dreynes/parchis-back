package miw.tfm.parchis.services;

import miw.tfm.parchis.models.GameState;
import miw.tfm.parchis.mongo.dto.GameStateEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SaveResource {
    private final GameState gameState;
    @Autowired
    private miw.tfm.parchis.repositories.GameStateRepository gameStateRepository;

    @Autowired
    public SaveResource(GameState gameState, miw.tfm.parchis.repositories.GameStateRepository gameStateRepository) {
        this.gameStateRepository = gameStateRepository;
        this.gameState = gameState;
    }

    public boolean saveGame(GameState gameState) {
        Optional<GameStateEntity> existingGame = gameStateRepository.findByGameNameAndUser_Username(gameState.getGameName(), gameState.getUser().getUsername());
        GameStateEntity gameStateEntity = new GameStateEntity(gameState);
        if (existingGame.isPresent()) {
            GameStateEntity existingGameState = existingGame.get();
            existingGameState.setParchis(gameStateEntity.getParchis());
            this.gameStateRepository.save(existingGameState);
        } else {
            this.gameStateRepository.save(gameStateEntity);
        }
        return true;
    }



    public Boolean canSave() {
        return this.gameState.getParchis().getDice().getValue()==0;
    }
}
