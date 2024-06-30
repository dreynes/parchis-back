package miw.tfm.parchis.mongo.dto;

import miw.tfm.parchis.models.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class ParchisEntity {
    @Id
    private String id;
    private Board board;
    private Turn turn;
    private Player[] players;
    private Dice dice;
    private boolean isCapture;
    private boolean isArriveGoal;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public ParchisEntity(Parchis parchis){
        this.board = parchis.getBoard();
        this.dice = parchis.getDice();
        this.turn = parchis.getTurn();
        this.players = parchis.getPlayers();
        this.isArriveGoal = parchis.isArriveGoal();
        this.isCapture = parchis.isCapture();
    }
    public ParchisEntity() {

    }
}
