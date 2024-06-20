package miw.tfm.parchis.models;

public class Parchis {
    private Board board;
    private Turn turn;
    private Player[] players;
    private Dice dice;

    public Parchis(){
        this.board = new Board();
        this.turn = new Turn(4);
        this.dice = new Dice();
        this.players = new Player[4];
        for(int i=0; i<4; i++){
            this.players[i] = new Player("jugador" + i, BoardConstants.COLORS.get(i));
        }
        setPutPiecesInitialPosition();
    }


    public void setPutPiecesInitialPosition() {
        for(Home home : this.board.getHomes()){
            for(int i=0;i<4;i++){
                Piece piece = new Piece(home.getColor());
                this.players[i].getPieces().add(piece);
                home.putPiece(piece);
            }
        }
    }


    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public Turn getTurn() {
        return turn;
    }

    public void setTurn(Turn turn) {
        this.turn = turn;
    }

    public Player[] getPlayers() {
        return players;
    }

    public void setPlayers(Player[] players) {
        this.players = players;
    }

    public Dice getDice() {
        return dice;
    }

    public void setDice(Dice dice) {
        this.dice = dice;
    }
}
