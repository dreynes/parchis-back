package miw.tfm.parchis.controllers;

import miw.tfm.parchis.models.UserModel;
import miw.tfm.parchis.mongo.dto.UserEntity;
import miw.tfm.parchis.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class RegisterController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserModel user) {
        if(userService.existUser(user)){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("The user just exist");
        }
        System.out.println(user);
        UserEntity newUser = userService.register(user);
        return ResponseEntity.ok(newUser);

    }

}
