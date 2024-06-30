package miw.tfm.parchis.services;

import miw.tfm.parchis.models.Parchis;
import org.springframework.stereotype.Service;

@Service
public class StartGameResource {
    public Parchis createGame(int numOfPlayers) {
        Parchis parchis = new Parchis(numOfPlayers);
        return parchis;
    }
}
