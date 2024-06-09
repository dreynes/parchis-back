package miw.tfm.parchis.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import miw.tfm.parchis.models.UserModel;
import miw.tfm.parchis.security.JwtUtil;
import miw.tfm.parchis.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.HashMap;
import java.util.Map;

@RestController
public class LogInController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    @Autowired
    public LogInController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/api/auth/login")
    public ResponseEntity<?> login(@RequestBody UserModel user) {
        System.out.println("llega al controller");
        if (userService.authenticate(user.getUsername(), user.getPassword())) {
            final String jwt = jwtUtil.generateToken(user.getUsername());
            Map<String, String> response = new HashMap<>();
            response.put("token", jwt);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
        }
    }
}
