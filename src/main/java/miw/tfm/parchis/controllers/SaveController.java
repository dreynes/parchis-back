package miw.tfm.parchis.controllers;

import miw.tfm.parchis.models.GameState;
import miw.tfm.parchis.services.SaveResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/game")
public class SaveController {
    private final GameState gameState;

    private SaveResource saveResource;

    @Autowired
    public SaveController(GameState gameState, SaveResource saveResource) {
        this.gameState = gameState;
        this.saveResource = saveResource;
    }
    @GetMapping("/canSave")
    public ResponseEntity<Boolean> canSave() {
        return ResponseEntity.ok(saveResource.canSave());
    }

    @PostMapping("/save")
    public ResponseEntity<Void> save(@RequestBody String gameName) {
        gameState.setGameName(gameName);
        saveResource.saveGame(gameState);
        return ResponseEntity.ok().build();
    }
}
