package miw.tfm.parchis;

import miw.tfm.parchis.models.UserModel;
import miw.tfm.parchis.mongo.dto.UserEntity;
import miw.tfm.parchis.mongo.repositories.UserRepository;
import miw.tfm.parchis.services.UserResource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

public class UserResourceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserResource userResource;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testExistUserReturnsTrueWhenUserExists() {
        UserModel user = new UserModel("existingUser", "password");
        when(userRepository.findByUsername(user.getUsername())).thenReturn(new UserEntity(user));

        assertTrue(userResource.existUser(user));
    }

    @Test
    public void testExistUserReturnsFalseWhenUserDoesNotExist() {
        UserModel user = new UserModel("nonExistingUser", "password");
        when(userRepository.findByUsername(user.getUsername())).thenReturn(null);

        assertFalse(userResource.existUser(user));
    }

    @Test
    public void testRegisterUser() {
        UserModel user = new UserModel("newUser", "password");
        UserEntity userEntity = new UserEntity(user);
        when(passwordEncoder.encode(user.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);

        UserEntity savedUser = userResource.register(user);

        assertEquals(savedUser.getPassword(), "password");
        assertTrue(savedUser.getUsername().equals("newUser"));
    }

    @Test
    public void testAuthenticateReturnsTrueWhenCredentialsAreCorrect() {
        UserModel user = new UserModel("validUser", "password");
        UserEntity userEntity = new UserEntity(user);
        when(userRepository.findByUsername(user.getUsername())).thenReturn(userEntity);
        when(passwordEncoder.matches("password", userEntity.getPassword())).thenReturn(true);

        assertTrue(userResource.authenticate("validUser", "password"));
    }

    @Test
    public void testAuthenticateReturnsFalseWhenCredentialsAreIncorrect() {
        UserModel user = new UserModel("invalidUser", "wrongPassword");
        UserEntity userEntity = new UserEntity(user);
        when(userRepository.findByUsername(user.getUsername())).thenReturn(userEntity);
        when(passwordEncoder.matches("wrongPassword", userEntity.getPassword())).thenReturn(false);

        assertFalse(userResource.authenticate("invalidUser", "wrongPassword"));
    }

    @Test
    public void testAuthenticateReturnsFalseWhenUserDoesNotExist() {
        when(userRepository.findByUsername("nonExistingUser")).thenReturn(null);

        assertFalse(userResource.authenticate("nonExistingUser", "password"));
    }
}
