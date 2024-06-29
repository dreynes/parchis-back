package miw.tfm.parchis.models;

import java.util.List;

public class Parchis {
    private Board board;
    private Turn turn;
    private Player[] players;
    private Dice dice;
    private boolean isCapture;
    private boolean isArriveGoal;

    public Parchis() {
        this.board = new Board();
        this.turn = new Turn(4);
        this.dice = new Dice();
        this.players = new Player[4];
        for (int i = 0; i < 4; i++) {
            this.players[i] = new Player("jugador" + i, BoardConstants.COLORS.get(i));
            this.players[i].setPath(BoardConstants.PATHS.get(i));
        }
        setPutPiecesInitialPosition();
    }


    public void setPutPiecesInitialPosition() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                Piece piece = new Piece(this.board.getHomes()[i].getColor());
                this.players[i].getPieces().add(piece);
                this.board.getHomes()[i].putPiece(piece);
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

    public Player getCurrentPlayer() {
        int turnValue = turn.getCurrentPlayer();
        return this.players[turnValue];
    }

    public List<Integer> getPlayerPath() {
        return this.getCurrentPlayer().getPath();
    }

    public void movePieceHome(Piece piece) {
        this.board.movePieceHome(piece);
    }

    public void putPieceGoal(Piece piece) {
        this.board.putPieceInGoal(piece);
        this.isArriveGoal = true;
    }

    public boolean isCapture() {
        return isCapture;
    }

    public void setCapture(boolean capture) {
        isCapture = capture;
    }

    public boolean isArriveGoal() {
        return isArriveGoal;
    }

    public void setArriveGoal(boolean arriveGoal) {
        isArriveGoal = arriveGoal;
    }
}
