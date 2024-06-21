package miw.tfm.parchis;

import miw.tfm.parchis.models.Dice;
import miw.tfm.parchis.models.Parchis;
import miw.tfm.parchis.models.SessionState;
import miw.tfm.parchis.services.PlayResource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class PlayResourceTest {

    @Mock
    private SessionState sessionState;

    @Mock
    private Parchis parchis;

    @Mock
    private Dice dice;

    @InjectMocks
    private PlayResource playResource;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(sessionState.getParchis()).thenReturn(parchis);
        when(parchis.getDice()).thenReturn(dice);
    }

    @Test
    void testRollDice() {
        when(dice.roll()).thenReturn(4);

        Integer result = playResource.rollDice();

        assertThat(result).isEqualTo(4);
        verify(dice, times(1)).roll();
    }
}
