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

    @BeforeEach
    public void setUp() {
        // Limpiar la base de datos de pruebas antes de cada prueba
        userRepository.deleteAll();
    }

    @Test
    public void testFindByUsername() {
        // Arrange: crear y guardar un usuario
        UserEntity user = new UserEntity();
        user.setUsername("testuserRepo");
        user.setPassword("password");
        userRepository.save(user);

        // Act: buscar el usuario por nombre de usuario
        UserEntity foundUser = userRepository.findByUsername("testuserRepo");

        // Assert: verificar que se encuentra el usuario correcto
        assertNotNull(foundUser);
        assertEquals("testuserRepo", foundUser.getUsername());
        assertEquals("password", foundUser.getPassword());
    }

    @Test
    public void testSaveAndRetrieveUser() {
        // Arrange: crear un nuevo usuario
        UserEntity user = new UserEntity();
        user.setUsername("anotheruser");
        user.setPassword("anotherpassword");

        // Act: guardar el usuario y obtener el ID generado
        UserEntity savedUser = userRepository.save(user);
        String userId = savedUser.getId();

        // Recuperar el usuario usando el ID
        UserEntity retrievedUser = userRepository.findById(userId).orElse(null);

        // Assert: verificar que se recupera el usuario correcto
        assertNotNull(retrievedUser);
        assertEquals("anotheruser", retrievedUser.getUsername());
        assertEquals("anotherpassword", retrievedUser.getPassword());
    }
}
