package miw.tfm.parchis;

import miw.tfm.parchis.controllers.StartGameController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest
public class StartGameControllerTest {

    @Autowired
    private StartGameController startGameController;


    @Test
    public void testCreateGameReturnsOk() {

        ResponseEntity<Map<String, Object>> response = startGameController.createGame();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
