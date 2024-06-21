package miw.tfm.parchis.models;


import java.util.ArrayList;
import java.util.List;

public class Square {
    private int value;
    private String color;
    private String type;
    private List<Piece> pieces;

    // Constructor
    public Square(int value, String color, String type) {
        this.value = value;
        this.color = color;
        this.type = type;
        this.pieces = new ArrayList<>();
    }

    public void putPiece(Piece piece){
        this.pieces.add(piece);

    }
    public boolean isEmpty(){
        return this.pieces.size()==0;
    }
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

    public int getPosition() {
        return this.value;
    }
}
