package miw.tfm.parchis;

import miw.tfm.parchis.controllers.PlayController;
import miw.tfm.parchis.models.*;
import miw.tfm.parchis.services.PlayResource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class PlayControllerTest {

    @Mock
    private GameState gameState;

    @Mock
    private PlayResource playResource;

    @Mock
    private Parchis parchis;

    @Mock
    private Board board;

    @InjectMocks
    private PlayController playController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(gameState.getParchis()).thenReturn(parchis);
        when(parchis.getBoard()).thenReturn(board);
    }

    @Test
    public void testRollDice() {
        when(playResource.rollDice()).thenReturn(5);
        ResponseEntity<Integer> response = playController.rollDice();
        assertEquals(5, response.getBody());
    }

    @Test
    public void testMustExitPiece() {
        when(playResource.mustExitPiece()).thenReturn(true);
        ResponseEntity<Boolean> response = playController.mustExitPiece();
        assertEquals(true, response.getBody());
    }

    @Test
    public void testExitPiece() {
        when(playResource.exitPiece()).thenReturn(new Piece("red"));
        ResponseEntity<Void> response = playController.exitPiece();
        assertEquals(ResponseEntity.ok().build(), response);
    }

    @Test
    public void testChangeTurn() {
        Color color = Color.ROJO;
        when(playResource.changeTurn()).thenReturn(color);
        ResponseEntity<Color> response = playController.changeTurn();
        assertEquals(color, response.getBody());
    }

    @Test
    public void testGetTurn() {
        Color color = Color.ROJO;
        when(playResource.getTurn()).thenReturn(color);
        ResponseEntity<Color> response = playController.getTurn();
        assertEquals(color, response.getBody());
    }
    @Test
    public void testCanMove() {
        when(playResource.canMove()).thenReturn(true);
        ResponseEntity<Boolean> response = playController.canMove();
        assertEquals(true, response.getBody());
    }

    @Test
    public void testMove() {
        Piece piece = new Piece("red");
        when(playResource.move(piece)).thenReturn(true);
        ResponseEntity<Boolean> response = playController.move(piece);
        assertEquals(true, response.getBody());
    }

    @Test
    public void testCapturePiece() {
        when(playResource.capturePiece()).thenReturn(true);
        ResponseEntity<Boolean> response = playController.capturePiece();
        assertEquals(true, response.getBody());
    }

    @Test
    public void testArriveGoal() {
        when(playResource.arriveGoal()).thenReturn(true);
        ResponseEntity<Boolean> response = playController.arriveGoal();
        assertEquals(true, response.getBody());
    }

    @Test
    public void testUpdateBoard() {

        Circuit circuit = mock(Circuit.class);
        Home[] homes = new Home[]{mock(Home.class)};
        FinalTrack[] finalTracks = new FinalTrack[]{mock(FinalTrack.class)};

        when(board.getBoard()).thenReturn(new Square[][]{});
        when(board.getCircuit()).thenReturn(circuit);
        when(circuit.getSquares()).thenReturn(Collections.emptyList());
        when(board.getHomes()).thenReturn(homes);
        when(board.getFinalTracks()).thenReturn(finalTracks);
        when(homes[0].getSquares()).thenReturn(Collections.emptyList());
        when(finalTracks[0].getSquares()).thenReturn(Collections.emptyList());

        ResponseEntity<Map<String, Object>> response = playController.updateBoard();
        Map<String, Object> responseBody = response.getBody();

        assertEquals(1, responseBody.size());
    }
}
