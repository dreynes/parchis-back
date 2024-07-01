package miw.tfm.parchis;

import miw.tfm.parchis.controllers.SaveController;
import miw.tfm.parchis.models.GameState;
import miw.tfm.parchis.services.SaveResource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class SaveControllerTest {

    @Mock
    private SaveResource saveResource;

    @Mock
    private GameState gameState;

    @InjectMocks
    private SaveController saveController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCanSave() {
        when(saveResource.canSave()).thenReturn(true);

        ResponseEntity<Boolean> response = saveController.canSave();

        verify(saveResource, times(1)).canSave();
        assertEquals(ResponseEntity.ok(true), response);
    }

    @Test
    public void testSave() {
        String gameName = "TestGame";

        ResponseEntity<Void> response = saveController.save(gameName);

        verify(gameState, times(1)).setGameName(gameName);
        verify(saveResource, times(1)).saveGame(gameState);
        assertEquals(ResponseEntity.ok().build(), response);
    }
}
