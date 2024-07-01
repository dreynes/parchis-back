package miw.tfm.parchis.models;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private String name;
    private String color;
    private List<Piece> pieces;
    private List<Integer> path;
    private boolean isTurn;

    public Player(String name, String color) {
        this.name = name;
        this.color = color;
        this.pieces = new ArrayList<>();
        this.isTurn = false;
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

    public void setPath(List<Integer> path) {
        this.path = path;
    }

    public List<Integer> getPath() {
        return this.path;
    }
}
