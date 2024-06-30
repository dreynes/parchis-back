package miw.tfm.parchis;

import miw.tfm.parchis.models.*;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;
import org.mockito.Mock;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

public abstract class BaseMockTest {

    @Mock
    protected GameState gameState;

    @Mock
    protected Parchis parchis;

    @Mock
    protected Dice dice;

    @Mock
    protected Turn turn;

    @Mock
    protected Board board;

    @Mock
    protected Circuit circuit;

    @Mock
    protected SquareExit squareExit;

    @Mock
    protected Home home;

    @Mock
    protected Player player;

    @Mock
    protected FinalTrack finalTrack;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        configureCommonMocks();
    }

    private void configureCommonMocks() {
        when(gameState.getParchis()).thenReturn(parchis);
        when(parchis.getDice()).thenReturn(dice);
        when(parchis.getTurn()).thenReturn(turn);
        when(parchis.getBoard()).thenReturn(board);
        when(board.getCircuit()).thenReturn(circuit);
        when(board.getHomes()).thenReturn(new Home[]{home});
        when(circuit.getExitSquare(anyInt())).thenReturn(squareExit);
        when(parchis.getCurrentPlayer()).thenReturn(player);
        when(board.getFinalTracks()).thenReturn(new FinalTrack[]{finalTrack});
    }
}
