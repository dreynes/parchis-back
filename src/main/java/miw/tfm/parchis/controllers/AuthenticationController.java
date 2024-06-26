package miw.tfm.parchis.controllers;

import miw.tfm.parchis.models.GameState;
import miw.tfm.parchis.models.UserModel;
import miw.tfm.parchis.mongo.dto.UserEntity;
import miw.tfm.parchis.security.JwtUtil;
import miw.tfm.parchis.services.UserResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    private UserResource userResource;
    private final JwtUtil jwtUtil;

    private final GameState gameState;
    @Autowired
    public AuthenticationController(UserResource userResource, JwtUtil jwtUtil, GameState gameState) {
        this.userResource = userResource;
        this.jwtUtil = jwtUtil;
        this.gameState = gameState;

    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserModel user) {
        if(userResource.existUser(user)){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("The user already exists");
        }
        System.out.println(user);
        UserEntity newUser = userResource.register(user);
        return ResponseEntity.ok(newUser);

    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserModel user) {
        if (userResource.authenticate(user.getUsername(), user.getPassword())) {
            final String jwt = jwtUtil.generateToken(user.getUsername());
            Map<String, String> response = new HashMap<>();
            response.put("token", jwt);
            gameState.setUser(user);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        gameState.setUser(null);
        return ResponseEntity.ok("Logout successful");
    }
}

