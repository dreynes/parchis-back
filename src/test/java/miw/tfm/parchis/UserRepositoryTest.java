package miw.tfm.parchis;

import miw.tfm.parchis.mongo.dto.UserEntity;
import miw.tfm.parchis.mongo.repositories.UserRepository;
import org.apache.catalina.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;

    @Test
    void findUserByUsername() {
        // Crea un usuario de prueba
        UserEntity user = new UserEntity();
        user.setUsername("testUser");
        user.setPassword("testPassword");

        // Guarda el usuario en la base de datos
        userRepository.save(user);

        // Busca el usuario por nombre de usuario
        UserEntity foundUser = userRepository.findByUsername("testUser");

        // Verifica que se haya encontrado un usuario
        assertNotNull(foundUser);

    }
}
