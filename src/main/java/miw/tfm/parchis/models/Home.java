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
                piece.setPosition(0);
                this.pieces.add(piece);
                return;
            }

        }
    }

    public Piece exitPiece() {
        Piece piece = null;
        for (Square square : squares) {
            if (!square.isEmpty()) {
                piece = square.getPieces().get(0);
                square.getPieces().remove(piece);
                this.pieces.remove(piece);
                return piece;
            }
        }
        return piece;
    }

    public boolean isEmpty(){
        return this.pieces.size()==0;
    }
    public boolean isFull(){
        return this.pieces.size()==4;
    }
    public String getColor() {
        return this.color;
    }
    public List<Piece> getPieces() {
        return pieces;
    }
}


