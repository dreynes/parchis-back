package miw.tfm.parchis.models;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private String name;
    private String color;
    private List<Piece> pieces;
    private boolean isTurn;

    public Player(String name, String color) {
        this.name = name;
        this.color = color;
        this.pieces = new ArrayList<>();
        this.isTurn = false;

        for (int i = 0; i < 4; i++) {
            this.pieces.add(new Piece(color));
        }
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public List<Piece> getPieces() {
        return pieces;
    }

    public boolean isTurn() {
        return isTurn;
    }

    public void setTurn(boolean turn) {
        isTurn = turn;
    }

}
