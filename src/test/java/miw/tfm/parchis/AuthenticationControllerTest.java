package miw.tfm.parchis;

import miw.tfm.parchis.controllers.AuthenticationController;
import miw.tfm.parchis.models.SessionState;
import miw.tfm.parchis.models.UserModel;
import miw.tfm.parchis.mongo.dto.UserEntity;
import miw.tfm.parchis.security.JwtUtil;
import miw.tfm.parchis.services.UserResource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class AuthenticationControllerTest {

    @Mock
    private UserResource userResource;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private SessionState sessionState;

    @InjectMocks
    private AuthenticationController authenticationController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegister() {

        UserModel userModel = new UserModel("testUser", "testPassword");

        when(userResource.existUser(any(UserModel.class))).thenReturn(false);
        when(userResource.register(any(UserModel.class))).thenReturn(new UserEntity());

        ResponseEntity<?> response = authenticationController.register(userModel);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }


    @Test
    public void testRegisterUserAlreadyExists() {
        UserModel user = new UserModel("existingUser", "password");
        when(userResource.existUser(user)).thenReturn(true);

        ResponseEntity<?> response = authenticationController.register(user);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("The user already exists", response.getBody());
    }

    @Test
    public void testLoginSuccess() {

        UserModel userModel = new UserModel("testUser", "testPassword");

        when(userResource.authenticate(any(String.class), any(String.class))).thenReturn(true);
        when(jwtUtil.generateToken(any(String.class))).thenReturn("fake-jwt-token");

        ResponseEntity<?> response = authenticationController.login(userModel);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isInstanceOf(Map.class);
        assertThat(((Map<?, ?>) response.getBody()).get("token")).isEqualTo("fake-jwt-token");
    }

    @Test
    public void testLoginFailure() {
        UserModel userModel = new UserModel("testUser","testPassword");

        when(userResource.authenticate(any(String.class), any(String.class))).thenReturn(false);

        ResponseEntity<?> response = authenticationController.login(userModel);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(response.getBody()).isEqualTo("Credenciales inválidas");
    }

    @Test
    public void testLogout() {
        ResponseEntity<?> response = authenticationController.logout();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo("Logout successful");
    }
}
