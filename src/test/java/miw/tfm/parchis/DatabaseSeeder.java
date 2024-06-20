package miw.tfm.parchis;

import jakarta.annotation.PostConstruct;
import miw.tfm.parchis.models.UserModel;
import miw.tfm.parchis.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class DatabaseSeeder {

    @Autowired
    private UserService userService;

    @PostConstruct
    public void seedDatabase() {
        if (!userService.existUser(new UserModel("testuser", "password"))) {
            userService.register(new UserModel("testuser", "password"));
        }
    }
}
