package miw.tfm.parchis;

import miw.tfm.parchis.models.GameState;
import miw.tfm.parchis.models.Parchis;
import miw.tfm.parchis.models.UserModel;
import miw.tfm.parchis.models.Dice;
import miw.tfm.parchis.mongo.dto.GameStateEntity;
import miw.tfm.parchis.repositories.GameStateRepository;
import miw.tfm.parchis.services.SaveResource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class SaveResourceTest {

    @Mock
    private GameStateRepository gameStateRepository;

    @Mock
    private GameState gameState;

    @Mock
    private Parchis parchis;

    @Mock
    private UserModel userModel;

    @InjectMocks
    private SaveResource saveResource;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(gameState.getParchis()).thenReturn(parchis);
        when(gameState.getUser()).thenReturn(userModel);
        when(userModel.getUsername()).thenReturn("testUser");
    }

    @Test
    public void testSaveGame() {
        when(gameState.getGameName()).thenReturn("TestGame");
        when(gameState.getUser()).thenReturn(new UserModel("userName", "pawd"));
        GameState gameState1 = new GameState();
        GameStateEntity mockGameStateEntity = new GameStateEntity(gameState);
        when(gameStateRepository.findByGameNameAndUser_Username(anyString(), anyString())).thenReturn(Optional.empty());

        boolean result = saveResource.saveGame(gameState);

        verify(gameStateRepository, times(1)).save(any(GameStateEntity.class));
        assertTrue(result);
    }

    @Test
    public void testCanSave() {
        Dice mockDice = mock(Dice.class);
        when(parchis.getDice()).thenReturn(mockDice);
        when(mockDice.getValue()).thenReturn(0);

        boolean result = saveResource.canSave();

        assertTrue(result);
    }
}
