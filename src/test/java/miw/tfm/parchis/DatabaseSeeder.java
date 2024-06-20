package miw.tfm.parchis;

import jakarta.annotation.PostConstruct;
import miw.tfm.parchis.models.UserModel;
import miw.tfm.parchis.services.UserResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class DatabaseSeeder {

    @Autowired
    private UserResource userResource;

    @PostConstruct
    public void seedDatabase() {
        if (!userResource.existUser(new UserModel("testuser", "password"))) {
            userResource.register(new UserModel("testuser", "password"));
        }
    }
}
