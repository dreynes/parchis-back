package miw.tfm.parchis;

import miw.tfm.parchis.controllers.PlayController;
import miw.tfm.parchis.models.SessionState;
import miw.tfm.parchis.services.PlayResource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class PlayControllerTest {

    @Mock
    private SessionState sessionState;

    @Mock
    private PlayResource playResource;

    @InjectMocks
    private PlayController playController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRollDice() {
        when(playResource.rollDice()).thenReturn(4);

        ResponseEntity<Integer> response = playController.rollDice();

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo(4);
    }
}
