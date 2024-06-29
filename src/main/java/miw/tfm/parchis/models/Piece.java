package miw.tfm.parchis.models;

import java.util.Objects;

public class Piece {

    private String color;
    private int position;

    public Piece() {
    }

    public Piece(String color) {
        this.color = color;
    }

    public String getColor() {
        return this.color;
    }

    public int getPosition() {
        return this.position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Piece piece = (Piece) o;
        return position == piece.position && Objects.equals(color, piece.color);
    }


    @Override
    public int hashCode() {
        return Objects.hash(color, position);
    }
}
