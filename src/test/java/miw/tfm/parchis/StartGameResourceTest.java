package miw.tfm.parchis;

import miw.tfm.parchis.models.Board;
import miw.tfm.parchis.models.Parchis;
import miw.tfm.parchis.services.StartGameResource;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class StartGameResourceTest {

    private final StartGameResource startGameResource = new StartGameResource();

    @Test
    public void testCreateGame() {
        Parchis parchis = startGameResource.createGame();
        Board board = parchis.getBoard();
        assertThat(board).isNotNull();
        assertThat(board.getBoard()).isNotNull().isNotEmpty();
        assertThat(board.getCircuit()).isNotNull();
        assertThat(board.getHomes()).isNotNull().isNotEmpty();
        assertThat(board.getFinalTracks()).isNotNull().isNotEmpty();

        assertThat(board.getBoard()).isNotEmpty();
        assertThat(board.getCircuit().getSquares()).isNotEmpty();
        assertThat(board.getHomes()).isNotEmpty();
        assertThat(board.getFinalTracks()).isNotEmpty();

        assertThat(board.getHomes()).allMatch(home -> home.getSquares() != null && !home.getSquares().isEmpty());
        assertThat(board.getFinalTracks()).allMatch(finalTrack -> finalTrack.getSquares() != null && !finalTrack.getSquares().isEmpty());
    }
}
