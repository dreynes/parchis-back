package miw.tfm.parchis.controllers;

import miw.tfm.parchis.models.SessionState;
import miw.tfm.parchis.services.PlayResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/game/play")
public class PlayController {

    private final SessionState sessionState;

    private PlayResource playResource;

    @Autowired
    public PlayController(SessionState sessionState, PlayResource playResource) {
        this.sessionState = sessionState;
        this.playResource = playResource;
    }
    @GetMapping("/rollDice")
    public ResponseEntity<Integer> rollDice() {
       return ResponseEntity.ok(playResource.rollDice());
    }
    @GetMapping("/canMove")
    public ResponseEntity<Boolean> canMove() {
        return ResponseEntity.ok(playResource.canMove());
    }
}
