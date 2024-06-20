package miw.tfm.parchis.services;

import miw.tfm.parchis.models.Board;
import miw.tfm.parchis.models.Parchis;
import org.springframework.stereotype.Service;

@Service
public class StartGameResource {
    public Parchis createGame() {
        Parchis parchis = new Parchis();
        return parchis;
    }
}
