package miw.tfm.parchis.controllers;

import jakarta.websocket.server.PathParam;
import miw.tfm.parchis.models.*;
import miw.tfm.parchis.services.OpenGameResource;
import miw.tfm.parchis.services.StartGameResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/game")
public class StartGameController {
    @Autowired
    private StartGameResource startGameResource;
    @Autowired
    private OpenGameResource openResource;

    private final GameState gameState;

    @Autowired
    public StartGameController(GameState gameState, StartGameResource startGameResource, OpenGameResource openResource) {
        this.gameState = gameState;
        this.startGameResource = startGameResource;
        this.openResource = openResource;
    }


    @PostMapping("/create/initializeBoard")
    public ResponseEntity<Void> createGame(@PathParam("numOfPlayers")  int numOfPlayers) {
        Parchis parchis = startGameResource.createGame(numOfPlayers);
        gameState.setParchis(parchis);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/open")
    public ResponseEntity<List<String>> open() {
        return ResponseEntity.ok(openResource.findGamesByUser(gameState.getUser().getUsername()));
    }
    @PostMapping("/openGame")
    public ResponseEntity<Void> openGame(@PathParam("gameName") String gameName) {
        List<GameState> gameStates = openResource.findGamesByName(gameName);
        gameState.setParchis(gameStates.get(0).getParchis());
        return ResponseEntity.ok().build();
    }

}

