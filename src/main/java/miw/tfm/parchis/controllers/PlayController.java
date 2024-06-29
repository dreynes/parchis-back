package miw.tfm.parchis.controllers;

import miw.tfm.parchis.models.*;
import miw.tfm.parchis.services.PlayResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

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
    @GetMapping("/mustExitPiece")
    public ResponseEntity<Boolean> mustExitPiece() {
        return ResponseEntity.ok(playResource.mustExitPiece());
    }
    @GetMapping("/exitPiece")
    public ResponseEntity<Void> exitPiece() {
        playResource.exitPiece();
        return ResponseEntity.ok().build();
    }
    @GetMapping("/changeTurn")
    public ResponseEntity<Color> changeTurn() {
        return ResponseEntity.ok(playResource.changeTurn());
    }
    @GetMapping("/canMove")
    public ResponseEntity<Boolean> canMove() {
        return ResponseEntity.ok(playResource.canMove());
    }
    @PostMapping("/move")
    public ResponseEntity<Boolean> move(@RequestBody Piece piece) {
        System.out.println("posicion de la ficha" + piece.getPosition() + "color de la ficha: " + piece.getColor());
        return ResponseEntity.ok(playResource.move(piece));
    }
    @GetMapping("/capturePiece")
    public ResponseEntity<Boolean> capturePiece() {
       return ResponseEntity.ok(playResource.capturePiece());
    }

    @GetMapping("/arriveGoal")
    public ResponseEntity<Boolean> arriveGoal() {
        return ResponseEntity.ok(playResource.arriveGoal());
    }

    @GetMapping("/updateBoard")
    public ResponseEntity<Map<String, Object>> updateBoard(){
        Parchis parchis = this.sessionState.getParchis();
        Board board = parchis.getBoard();
        Map<String, Object> response = new HashMap<>();
        response.put("board", board.getBoard());
        response.put("circuit", board.getCircuit().getSquares());
        response.put("homes", Arrays.stream(board.getHomes()).map(Home::getSquares).collect(Collectors.toList()));
        response.put("finalTracks", Arrays.stream(board.getFinalTracks()).map(FinalTrack::getSquares).collect(Collectors.toList()));
        return ResponseEntity.ok(response);
    }
}
