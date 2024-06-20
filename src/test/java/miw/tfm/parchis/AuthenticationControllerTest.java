package miw.tfm.parchis;

import miw.tfm.parchis.controllers.AuthenticationController;
import miw.tfm.parchis.models.UserModel;
import miw.tfm.parchis.mongo.dto.UserEntity;
import miw.tfm.parchis.security.JwtUtil;
import miw.tfm.parchis.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class AuthenticationControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private AuthenticationController authenticationController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testRegisterUserAlreadyExists() {
        UserModel user = new UserModel("existingUser", "password");
        when(userService.existUser(user)).thenReturn(true);

        ResponseEntity<?> response = authenticationController.register(user);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("The user just exist", response.getBody());
    }

    @Test
    public void testRegisterUserSuccess() {
        UserModel user = new UserModel("newUser", "password");
        when(userService.existUser(user)).thenReturn(false);
        UserEntity expectedUser = new UserEntity(user);
        when(userService.register(user)).thenReturn(expectedUser);

        ResponseEntity<?> response = authenticationController.register(user);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        UserEntity actualUser = (UserEntity) response.getBody();
        assertEquals(expectedUser.getUsername(), actualUser.getUsername());
        assertEquals(expectedUser.getPassword(), actualUser.getPassword());
    }

    @Test
    public void testLoginSuccess() {
        UserModel user = new UserModel("validUser", "password");
        when(userService.authenticate(user.getUsername(), user.getPassword())).thenReturn(true);
        when(jwtUtil.generateToken(user.getUsername())).thenReturn("jwtToken");

        ResponseEntity<?> response = authenticationController.login(user);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Map.of("token", "jwtToken"), response.getBody());
    }

    @Test
    public void testLoginFailure() {
        UserModel user = new UserModel("invalidUser", "wrongPassword");
        when(userService.authenticate(user.getUsername(), user.getPassword())).thenReturn(false);

        ResponseEntity<?> response = authenticationController.login(user);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Credenciales inválidas", response.getBody());
    }
}
