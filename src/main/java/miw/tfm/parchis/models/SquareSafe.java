package miw.tfm.parchis.models;

public class SquareSafe extends Square {
    public SquareSafe(int value, String color, String type) {
        super(value, color, type);
    }

    public boolean isFull() {
        return this.getPieces().size() == 2;
    }

    public void putPiece(Piece piece){
        if(this.getPieces().size()<=1) {
            piece.setPosition(this.getPosition());
            this.getPieces().add(piece);
        }
    }
    public boolean canPutPiece() {
        return !this.isFull();
    }

    public boolean hasBlockade() {
        if (this.getPieces().size() == 2)
            return this.getPieces().get(0).getColor().equals(this.getPieces().get(1).getColor());
        return false;
    }

    public boolean capturePiece() {
        return false;
    }
}

