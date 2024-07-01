package miw.tfm.parchis;

import miw.tfm.parchis.models.UserModel;
import miw.tfm.parchis.mongo.dto.GameStateEntity;
import miw.tfm.parchis.mongo.dto.ParchisEntity;
import miw.tfm.parchis.mongo.dto.UserEntity;
import miw.tfm.parchis.repositories.GameStateRepository;
import miw.tfm.parchis.services.OpenGameResource;
import miw.tfm.parchis.models.GameState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class OpenGameResourceTest {

    @Mock
    private GameStateRepository gameStateRepository;

    @InjectMocks
    private OpenGameResource openGameResource;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindGamesByUser() {
        String username = "testUser";
        GameStateEntity mockGameStateEntity1 = new GameStateEntity();
        mockGameStateEntity1.setGameName("Game1");
        mockGameStateEntity1.setUser(new UserEntity(new UserModel(username, "password")));
        mockGameStateEntity1.setParchis(new ParchisEntity());

        GameStateEntity mockGameStateEntity2 = new GameStateEntity();
        mockGameStateEntity2.setGameName("Game2");
        mockGameStateEntity2.setUser(new UserEntity(new UserModel(username, "password")));
        mockGameStateEntity2.setParchis(new ParchisEntity());

        List<GameStateEntity> mockGameStates = Arrays.asList(mockGameStateEntity1, mockGameStateEntity2);
        when(gameStateRepository.findByUser_Username(username)).thenReturn(mockGameStates);

        List<String> result = openGameResource.findGamesByUser(username);

        assertEquals(2, result.size());
        assertEquals("Game1", result.get(0));
        assertEquals("Game2", result.get(1));
    }

    @Test
    public void testFindGamesByName() {
        String gameName = "TestGame";
        ParchisEntity parchisEntity1 = new ParchisEntity();
        ParchisEntity parchisEntity2 = new ParchisEntity();

        GameStateEntity mockGameStateEntity1 = new GameStateEntity();
        mockGameStateEntity1.setGameName(gameName);
        mockGameStateEntity1.setUser(new UserEntity(new UserModel("user1", "password")));
        mockGameStateEntity1.setParchis(parchisEntity1);

        GameStateEntity mockGameStateEntity2 = new GameStateEntity();
        mockGameStateEntity2.setGameName(gameName);
        mockGameStateEntity2.setUser(new UserEntity(new UserModel("user2", "password")));
        mockGameStateEntity2.setParchis(parchisEntity2);

        List<GameStateEntity> mockGameStates = Arrays.asList(mockGameStateEntity1, mockGameStateEntity2);
        when(gameStateRepository.findByGameName(gameName)).thenReturn(mockGameStates);

        List<GameState> result = openGameResource.findGamesByName(gameName);

        assertEquals(2, result.size());
        assertEquals(gameName, result.get(0).getGameName());
        assertEquals(gameName, result.get(1).getGameName());
    }
}
