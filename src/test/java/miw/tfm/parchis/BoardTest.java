package miw.tfm.parchis;

import miw.tfm.parchis.models.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class BoardTest {

    private Board board;

    @BeforeEach
    public void setUp() {
        board = new Board();
    }

    @Test
    public void testBoardInitialization() {
        assertThat(board.getBoard()).isNotNull();

        assertThat(board.getBoard().length).isEqualTo(17);
        assertThat(board.getBoard()[0].length).isEqualTo(17);
    }


    @Test
    public void testHomesInitialization() {
        assertThat(board.getHomes()).isNotNull();
        assertThat(board.getHomes().length).isEqualTo(4);

        for (int i = 0; i < 4; i++) {
            Home home = board.getHomes()[i];
            assertThat(home).isNotNull();
            assertThat(home.getSquares()).isNotEmpty();
        }
    }

    @Test
    public void testFinalTracksInitialization() {
        assertThat(board.getFinalTracks()).isNotNull();
        assertThat(board.getFinalTracks().length).isEqualTo(4);

        for (int i = 0; i < 4; i++) {
            FinalTrack finalTrack = board.getFinalTracks()[i];
            assertThat(finalTrack).isNotNull();
            assertThat(finalTrack.getSquares()).isNotEmpty();
        }
    }

    @Test
    public void testSafeSquaresInitialization() {
        for (Integer value : BoardConstants.SQUARE_SAFE_VALUES) {
            Coordinate coord = board.getPositionFromValue(value);
            Square square = board.getBoard()[coord.getRow()][coord.getCol()];
            assertThat(square).isInstanceOf(SquareSafe.class);
        }
    }

    @Test
    public void testGoalSquareInitialization() {
        Square goalSquare = board.getBoard()[8][8];
        assertThat(goalSquare).isInstanceOf(Goal.class);
        assertThat(goalSquare.getPosition()).isEqualTo(145);
    }
}
