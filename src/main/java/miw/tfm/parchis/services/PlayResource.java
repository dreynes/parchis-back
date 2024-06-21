package miw.tfm.parchis.services;

import miw.tfm.parchis.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayResource {


    private final SessionState sessionState;

    @Autowired
    public PlayResource(SessionState sessionState) {
        this.sessionState = sessionState;
    }

    public Integer rollDice(){
        Dice dice = sessionState.getParchis().getDice();
        return dice.roll();
    }
    public boolean canMove(){
        int diceValue = 0;
        diceValue = this.sessionState.getParchis().getDice().getValue();
        if(diceValue == 5){
            return canExitPiece();
        }else {
            return true;
        }
    }
    public boolean canExitPiece(){
        Turn turn = this.sessionState.getParchis().getTurn();
        int turnValue = turn.getCurrentPlayer();
        Board board = this.sessionState.getParchis().getBoard();
        Home home = board.getHomes()[turnValue];
        for(Square square: home.getSquares()) {
            if (square.getPieces().size() > 0) {
                return true;
            }
        }
            return false;
    }

}


