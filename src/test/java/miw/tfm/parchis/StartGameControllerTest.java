package miw.tfm.parchis;

import miw.tfm.parchis.controllers.StartGameController;
import miw.tfm.parchis.models.GameState;
import miw.tfm.parchis.models.Parchis;
import miw.tfm.parchis.models.UserModel;
import miw.tfm.parchis.services.OpenGameResource;
import miw.tfm.parchis.services.StartGameResource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class StartGameControllerTest {

    @Mock
    private StartGameResource startGameResource;

    @Mock
    private OpenGameResource openGameResource;

    @Mock
    private GameState gameState;

    @InjectMocks
    private StartGameController startGameController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateGame() {
        Parchis mockParchis = new Parchis(4);
        when(startGameResource.createGame(4)).thenReturn(mockParchis);
        ResponseEntity<Void> response = startGameController.createGame(4);
        verify(startGameResource, times(1)).createGame(4);
        verify(gameState, times(1)).setParchis(mockParchis);
        assertEquals(ResponseEntity.ok().build(), response);
    }

    @Test
    public void testOpen() {
        List<String> mockGameList = Arrays.asList("Game1", "Game2");
        when(gameState.getUser()).thenReturn(new UserModel("testUser", "pwd"));
        when(openGameResource.findGamesByUser("testUser")).thenReturn(mockGameList);

        ResponseEntity<List<String>> response = startGameController.open();

        verify(openGameResource, times(1)).findGamesByUser("testUser");
        assertEquals(ResponseEntity.ok(mockGameList), response);
    }

    @Test
    public void testOpenGame() {
        String gameName = "TestGame";
        Parchis mockParchis = new Parchis(4);
        GameState mockGameState = new GameState();
        mockGameState.setParchis(mockParchis);
        List<GameState> mockGameStateList = Arrays.asList(mockGameState);

        when(openGameResource.findGamesByName(gameName)).thenReturn(mockGameStateList);

        ResponseEntity<Void> response = startGameController.openGame(gameName);

        verify(openGameResource, times(1)).findGamesByName(gameName);
        verify(gameState, times(1)).setParchis(mockParchis);
        assertEquals(ResponseEntity.ok().build(), response);
    }
}
