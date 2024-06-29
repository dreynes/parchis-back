package miw.tfm.parchis.models;

import java.util.ArrayList;
import java.util.List;

public class Circuit {
    private List<Square> squares;

    public Circuit() {
        this.squares = new ArrayList<>();
    }

    public SquareExit getExitSquare(int turnValue) {
        int squareValue = BoardConstants.SQUARE_EXIT.get(turnValue);
        for (Square square : squares) {
            if (square.getValue() == squareValue) {
                return (SquareExit) square;
            }
        }
        return null;
    }

    public List<Square> getSquares() {
        return squares;
    }
}
