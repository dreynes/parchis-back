package miw.tfm.parchis.models;


import java.util.ArrayList;
import java.util.List;

public class Circuit {
    private List<Square> squares;

    public Circuit() {
        this.squares = new ArrayList<>();
    }

    public void addSquare(Square square) {
        this.squares.add(square);
    }

    public List<Square> getSquares() {
        return squares;
    }

    public void setSquares(List<Square> squares) {
        this.squares = squares;
    }
}
