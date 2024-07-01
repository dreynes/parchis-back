package miw.tfm.parchis.services;

import miw.tfm.parchis.models.GameState;
import miw.tfm.parchis.mongo.dto.GameStateEntity;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public class OpenGameResource {
    @Autowired
    miw.tfm.parchis.repositories.GameStateRepository gameStateRepository;

    public List<String> findGamesByUser(String username) {

        List<GameStateEntity> gameStates = gameStateRepository.findByUser_Username(username);
        return gameStates.stream()
                .map(GameStateEntity::getGameName)
                .collect(Collectors.toList());
    }

    public List<GameState> findGamesByName(String gameName) {
        List<GameStateEntity> gameStates = gameStateRepository.findByGameName(gameName);
        return gameStates.stream()
                .map(GameState::new)
                .collect(Collectors.toList());
    }
}
