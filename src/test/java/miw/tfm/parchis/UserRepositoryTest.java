package miw.tfm.parchis;

import miw.tfm.parchis.mongo.dto.UserEntity;
import miw.tfm.parchis.mongo.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@DataMongoTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindByUsername() {
        UserEntity user = new UserEntity();
        user.setUsername("testuserRepo");
        user.setPassword("password");
        userRepository.save(user);

        UserEntity foundUser = userRepository.findByUsername("testuserRepo");

        assertNotNull(foundUser);
        assertEquals("testuserRepo", foundUser.getUsername());
        assertEquals("password", foundUser.getPassword());

        // Eliminamos el usuario registrado
        userRepository.deleteByUsername("testuserRepo");
    }

    @Test
    public void testSaveAndRetrieveUser() {
        UserEntity user = new UserEntity();
        user.setUsername("anotheruser");
        user.setPassword("anotherpassword");
        UserEntity savedUser = userRepository.save(user);
        String userId = savedUser.getId();

        UserEntity retrievedUser = userRepository.findById(userId).orElse(null);

        assertNotNull(retrievedUser);
        assertEquals("anotheruser", retrievedUser.getUsername());
        assertEquals("anotherpassword", retrievedUser.getPassword());

        userRepository.deleteById(userId);
    }
}
