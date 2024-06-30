package miw.tfm.parchis.models;

public class SquareExit extends SquareSafe {
    public SquareExit(int value, String color, String type) {
        super(value, color, type);
    }
    public boolean canPutPiece(){
        return !this.isFull();
    }
    public void putPiece(Piece piece){
        if(this.getPieces().size()<=1) {
            piece.setPosition(this.getPosition());
            this.getPieces().add(piece);
        }
    }
    public boolean hasBlockade() {
        System.out.println("comprueba bloqueo exit");
        if(this.getPieces().size()==2){
            return this.getPieces().get(0).getColor().equals(this.getPieces().get(1).getColor());
        }
        return false;
    }

}