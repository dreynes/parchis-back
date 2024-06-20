package miw.tfm.parchis.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class PlayController {

    @GetMapping("/play/rollDice")
    public ResponseEntity<Integer> rollDice() {
       return ResponseEntity.ok(6);
    }
}
