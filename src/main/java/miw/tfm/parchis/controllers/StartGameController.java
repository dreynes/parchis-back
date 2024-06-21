package miw.tfm.parchis.controllers;

        import miw.tfm.parchis.models.*;
        import miw.tfm.parchis.security.JwtUtil;
        import miw.tfm.parchis.services.StartGameResource;
        import miw.tfm.parchis.services.UserResource;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.http.ResponseEntity;
        import org.springframework.web.bind.annotation.GetMapping;
        import org.springframework.web.bind.annotation.RequestMapping;
        import org.springframework.web.bind.annotation.RestController;

        import java.util.Arrays;
        import java.util.HashMap;
        import java.util.Map;
        import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/game")
public class StartGameController {
    @Autowired
    private StartGameResource startGameResource;

    private final SessionState sessionState;

    @Autowired
    public StartGameController(SessionState sessionState) {
        this.sessionState = sessionState;
    }


    @GetMapping("/create/initializeBoard")
    public ResponseEntity<Map<String, Object>> createGame() {
        Parchis parchis = startGameResource.createGame();
        sessionState.setParchis(parchis);
        Board board = parchis.getBoard();
        Map<String, Object> response = new HashMap<>();
        response.put("board", board.getBoard());
        response.put("circuit", board.getCircuit().getSquares());
        response.put("homes", Arrays.stream(board.getHomes()).map(Home::getSquares).collect(Collectors.toList()));
        response.put("finalTracks", Arrays.stream(board.getFinalTracks()).map(FinalTrack::getSquares).collect(Collectors.toList()));
        return ResponseEntity.ok(response);
    }

}

