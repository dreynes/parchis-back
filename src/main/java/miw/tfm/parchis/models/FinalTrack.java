package miw.tfm.parchis.models;

import java.util.ArrayList;
import java.util.List;

public class FinalTrack {
    private List<SquareSafe> squares;

    public FinalTrack() {
        this.squares = new ArrayList<>();
    }

    public void addSquare(SquareSafe square) {
        this.squares.add(square);
    }

    public List<SquareSafe> getSquares() {
        return squares;
    }


    public void setSquares(List<SquareSafe> squares) {
        this.squares = squares;
    }


}
