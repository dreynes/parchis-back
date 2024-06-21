package miw.tfm.parchis.services;

import miw.tfm.parchis.models.Dice;
import miw.tfm.parchis.models.SessionState;
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

}
