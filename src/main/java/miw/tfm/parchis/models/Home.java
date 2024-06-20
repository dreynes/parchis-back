package miw.tfm.parchis.models;

import java.util.ArrayList;
import java.util.List;

public class Home {
    private List<Square> squares;
    private List<Piece> pieces;
    private String color;
    public Home(String color) {
        this.color = color;
        this.squares = new ArrayList<>();
        this.pieces = new ArrayList<>();
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

    public void putPiece(Piece piece) {
        for (Square square : squares) {
            if (square.isEmpty()) {
                square.putPiece(piece);
                this.pieces.add(piece);
                return;
            }

        }
    }
    public String getColor() {
        return this.color;
    }
}


