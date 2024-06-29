package miw.tfm.parchis;

import miw.tfm.parchis.models.*;
import miw.tfm.parchis.services.PlayResource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PlayResourceTest extends BaseMockTest {

    @InjectMocks
    private PlayResource playResource;

    @BeforeEach
    public void setUp() {
        super.setUp();
    }

    @Test
    public void testRollDice() {
        when(dice.getValue()).thenReturn(0);
        when(dice.roll()).thenReturn(5);
        assertEquals(5, playResource.rollDice());

        when(dice.getValue()).thenReturn(4);
        assertEquals(4, playResource.rollDice());
    }

    @Test
    public void testMustExitPiece() {
        when(turn.getCurrentPlayer()).thenReturn(0);
        when(dice.getValue()).thenReturn(5);
        when(home.getPieces()).thenReturn(Collections.singletonList(new Piece("red")));
        when(squareExit.isFull()).thenReturn(false);

        assertTrue(playResource.mustExitPiece());

        when(dice.getValue()).thenReturn(4);
        assertFalse(playResource.mustExitPiece());
    }

    @Test
    public void testExitPiece() {
        when(turn.getCurrentPlayer()).thenReturn(0);
        Piece piece = new Piece("red");
        when(home.exitPiece()).thenReturn(piece);

        playResource.exitPiece();

        verify(squareExit, times(1)).putPiece(piece);
    }

    @Test
    public void testChangeTurn() {
        when(turn.getCurrentPlayer()).thenReturn(0);
        doNothing().when(turn).nextTurn();

        assertEquals(Color.values()[0], playResource.changeTurn());

        verify(turn, times(1)).nextTurn();
        verify(dice, times(1)).setValue(0);
    }

    @Test
    public void testAllPiecesInHome() {
        when(turn.getCurrentPlayer()).thenReturn(0);
        when(home.isFull()).thenReturn(true);

        assertTrue(playResource.allPiecesInHome());
    }

    @Test
    public void testCanMove() {
        when(dice.getValue()).thenReturn(5);
        when(player.getPath()).thenReturn(Collections.emptyList());
        when(player.getPieces()).thenReturn(Collections.singletonList(new Piece("red")));

        assertFalse(playResource.canMove());
    }

    @Test
    public void testIsValidMoveInFinalTrack() {
        when(turn.getCurrentPlayer()).thenReturn(0);
        when(board.getSquareFromValue(anyInt())).thenReturn(new Square(1, "RED", "NORMAL"));
        when(finalTrack.getSquares()).thenReturn(Collections.singletonList(new SquareSafe(1, "RED", "SAFE")));

        assertFalse(playResource.isValidMoveInFinalTrack(0, 6));
    }

    @Test
    public void testIsValidMove() {
        Piece piece = new Piece("red");
        when(player.getPath()).thenReturn(Collections.singletonList(1));
        when(board.getSquareFromValue(anyInt())).thenReturn(new Square(1, "red", "NORMAL"));

        assertFalse(playResource.isValidMove(piece, 6, Collections.singletonList(1)));
    }

    @Test
    public void testMove() {
        Piece piece = new Piece("red");
        when(dice.getValue()).thenReturn(5);
        when(player.getColor()).thenReturn("red");
        when(player.getPieces()).thenReturn(Collections.singletonList(piece));
        when(player.getPath()).thenReturn(Collections.singletonList(1));

        assertFalse(playResource.move(piece));
    }

    @Test
    public void testMovePiece() {
        Piece piece = new Piece("red");
        List<Integer> path = Arrays.asList(1, 2, 3, 4, 5);

        Square initialSquare = new Square(1, "RED", "NORMAL");
        initialSquare.putPiece(piece);

        Square finalSquare = new Square(2, "RED", "NORMAL");

        when(turn.getCurrentPlayer()).thenReturn(0);
        when(board.getSquareFromValue(1)).thenReturn(initialSquare);
        when(board.getSquareFromValue(2)).thenReturn(finalSquare);
        List<SquareSafe> finalTrackSquares = Arrays.asList(new SquareSafe(1, "RED", "SAFE"),
                new SquareSafe(2, "RED", "SAFE"));
        when(finalTrack.getSquares()).thenReturn(finalTrackSquares);
        when(board.getFinalTracks()).thenReturn(new FinalTrack[]{finalTrack});

        playResource.movePiece(piece, 1, path);

        verify(board, times(1)).getSquareFromValue(1);
        verify(board, times(1)).getSquareFromValue(2);
        assertFalse(initialSquare.getPieces().contains(piece));
        assertTrue(finalSquare.getPieces().contains(piece));
    }


}
