package miw.tfm.parchis;

import miw.tfm.parchis.models.*;
import miw.tfm.parchis.services.PlayResource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class PlayResourceTest {

    @Mock
    private SessionState sessionState;

    @Mock
    private Parchis parchis;

    @Mock
    private Dice dice;

    @Mock
    private Turn turn;

    @Mock
    private Board board;

    @Mock
    private Home home;

    @InjectMocks
    private PlayResource playResource;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(sessionState.getParchis()).thenReturn(parchis);
        when(parchis.getDice()).thenReturn(dice);
        when(parchis.getTurn()).thenReturn(turn);
        when(parchis.getBoard()).thenReturn(board);
    }

    @Test
    void testRollDice() {
        when(dice.roll()).thenReturn(4);

        Integer result = playResource.rollDice();

        assertThat(result).isEqualTo(4);
        verify(dice, times(1)).roll();
    }

    @Test
    void testCanMoveWithDiceValue5AndCanExitPiece() {
        when(dice.getValue()).thenReturn(5);
        when(turn.getCurrentPlayer()).thenReturn(0);
        when(board.getHomes()).thenReturn(new Home[]{home});
        Square square = new Square(1, "red", "HomeSquare");
        square.putPiece(new Piece("red"));
        when(home.getSquares()).thenReturn(Collections.singletonList(square));

        boolean result = playResource.canMove();

        assertThat(result).isTrue();
    }

    @Test
    void testCanMoveWithDiceValue5AndCannotExitPiece() {
        when(dice.getValue()).thenReturn(5);
        when(turn.getCurrentPlayer()).thenReturn(0);
        when(board.getHomes()).thenReturn(new Home[]{home});
        when(home.getSquares()).thenReturn(Collections.singletonList(new Square(1, "red", "HomeSquare")));

        boolean result = playResource.canMove();

        assertThat(result).isFalse();
    }

    @Test
    void testCanMoveWithDiceValueNot5() {
        when(dice.getValue()).thenReturn(4);

        boolean result = playResource.canMove();

        assertThat(result).isTrue();
    }

    @Test
    void testCanExitPieceWhenPieceExists() {
        Square square = new Square(1, "red", "HomeSquare");
        square.putPiece(new Piece("red"));

        when(turn.getCurrentPlayer()).thenReturn(0);
        when(board.getHomes()).thenReturn(new Home[]{home});
        when(home.getSquares()).thenReturn(Collections.singletonList(square));

        boolean result = playResource.canExitPiece();

        assertThat(result).isTrue();
    }

    @Test
    void testCanExitPieceWhenNoPieceExists() {
        Square square = new Square(1, "red", "HomeSquare");

        when(turn.getCurrentPlayer()).thenReturn(0);
        when(board.getHomes()).thenReturn(new Home[]{home});
        when(home.getSquares()).thenReturn(Collections.singletonList(square));

        boolean result = playResource.canExitPiece();

        assertThat(result).isFalse();
    }
}
