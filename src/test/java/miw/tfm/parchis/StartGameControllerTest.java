package miw.tfm.parchis;

import miw.tfm.parchis.controllers.StartGameController;
import miw.tfm.parchis.models.Parchis;
import miw.tfm.parchis.models.GameState;
import miw.tfm.parchis.services.StartGameResource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class StartGameControllerTest {

    @Mock
    private StartGameResource startGameResource;

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
        Parchis mockParchis = new Parchis();
        when(startGameResource.createGame()).thenReturn(mockParchis);
        ResponseEntity<Void> response = startGameController.createGame();
        verify(startGameResource, times(1)).createGame();
        verify(gameState, times(1)).setParchis(mockParchis);
        assertEquals(ResponseEntity.ok().build(), response);
    }
}
