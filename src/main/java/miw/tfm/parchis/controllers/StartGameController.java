package miw.tfm.parchis.controllers;

import miw.tfm.parchis.models.*;
import miw.tfm.parchis.services.StartGameResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/game")
public class StartGameController {
    @Autowired
    private StartGameResource startGameResource;

    private final SessionState sessionState;

    @Autowired
    public StartGameController(SessionState sessionState, StartGameResource startGameResource) {
        this.sessionState = sessionState;
        this.startGameResource = startGameResource;
    }


    @PostMapping("/create/initializeBoard")
    public ResponseEntity<Void> createGame() {
        Parchis parchis = startGameResource.createGame();
        sessionState.setParchis(parchis);
        return ResponseEntity.ok().build();
    }

}

