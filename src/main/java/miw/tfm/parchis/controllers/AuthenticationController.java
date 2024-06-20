package miw.tfm.parchis.controllers;

import miw.tfm.parchis.models.UserModel;
import miw.tfm.parchis.mongo.dto.UserEntity;
import miw.tfm.parchis.security.JwtUtil;
import miw.tfm.parchis.services.UserService;
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
    private UserService userService;
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthenticationController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserModel user) {
        if(userService.existUser(user)){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("The user just exist");
        }
        System.out.println(user);
        UserEntity newUser = userService.register(user);
        return ResponseEntity.ok(newUser);

    }


    @PostMapping("/login")
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

