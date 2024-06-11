package miw.tfm.parchis.models;

import miw.tfm.parchis.models.Piece;

import java.util.ArrayList;
import java.util.List;

public class Square {
    private int value;
    private String color;
    private String type;
    private List<Piece> pieces = new ArrayList<>();

    // Constructor
    public Square(int value, String color, String type) {
        this.value = value;
        this.color = color;
        this.type = type;
    }

    // Getters y Setters
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Piece> getPieces() {
        return pieces;
    }

    public void setPieces(List<Piece> pieces) {
        this.pieces = pieces;
    }
}
