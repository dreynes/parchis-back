package miw.tfm.parchis.services;

import miw.tfm.parchis.models.Board;
import org.springframework.stereotype.Service;

@Service
public class StartGameService {
    public Board createGame() {
        return new Board();
    }
}
