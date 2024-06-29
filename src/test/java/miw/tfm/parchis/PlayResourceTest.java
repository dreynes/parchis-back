package miw.tfm.parchis;

import miw.tfm.parchis.models.*;
import miw.tfm.parchis.services.PlayResource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class PlayResourceTest {

    private Parchis parchis;
    private PlayResource playResource;

    @BeforeEach
    void setUp() {
        // Inicializar el estado del juego (Parchis)
        parchis = new Parchis();
        SessionState sessionState = new SessionState();
        sessionState.setParchis(parchis);
        playResource = new PlayResource(sessionState);
    }


    @Test
    void rollDice_whenDiceIsUnrolled_shouldReturnRolledValue() {
        Dice dice = parchis.getDice();
        dice.setValue(0);

        int result = playResource.rollDice();

        assertTrue(result >= 1 && result <= 6, "El resultado del dado debe estar entre 1 y 6");
    }

    @Test
    void rollDice_whenDiceValueAlreadySet_shouldReturnSameValue() {
        Dice dice = parchis.getDice();
        dice.setValue(4);
        int result = playResource.rollDice();
        assertEquals(4, result, "El resultado del dado debería ser igual al valor predefinido");
    }


    @Test
    void mustExitPiece_whenConditionsNotMet_shouldReturnFalse() {
        Turn turn = parchis.getTurn();
        turn.setCurrentPlayer(0);

        Dice dice = parchis.getDice();
        dice.setValue(4); // Otro valor diferente a 5

        Home home = parchis.getBoard().getHomes()[0];
        home.putPiece(new Piece(BoardConstants.COLORS.get(0)));

        boolean result = playResource.mustExitPiece();

        assertFalse(result, "No se cumple la condición para salir la pieza");
    }

    @Test
    void mustExitPiece_whenConditionsMet_shouldReturnTrue() {
        Turn turn = parchis.getTurn();
        turn.setCurrentPlayer(0);

        Dice dice = parchis.getDice();
        dice.setValue(5);

        Home home = parchis.getBoard().getHomes()[0];
        home.putPiece(new Piece(BoardConstants.COLORS.get(0)));

        boolean result = playResource.mustExitPiece();

        assertTrue(result);
    }

    @Test
    void exitPiece_shouldMovePieceToExitSquare() {
        Turn turn = parchis.getTurn();
        turn.setCurrentPlayer(0);

        Home home = parchis.getBoard().getHomes()[0];
        Piece piece = new Piece(BoardConstants.COLORS.get(0));
        home.putPiece(piece);

        Circuit circuit = parchis.getBoard().getCircuit();
        SquareExit squareExit = circuit.getExitSquare(0);
        int initialExitSize = squareExit.getPieces().size();

        playResource.exitPiece();

        assertEquals(initialExitSize + 1, squareExit.getPieces().size(), "La pieza debería haber sido movida a la salida");
    }

    @Test
    void changeTurn_whenChangingTurn_shouldCycleThroughPlayers() {
        Turn turn = parchis.getTurn();
        int initialPlayer = turn.getCurrentPlayer();

        playResource.changeTurn(); // Cambiar turno una vez
        playResource.changeTurn(); // Cambiar turno una vez más
        playResource.changeTurn(); // Cambiar turno una vez más
        playResource.changeTurn(); // Cambiar turno una vez más

        assertEquals(initialPlayer, turn.getCurrentPlayer(), "Se ha completado el ciclo deberia tocar el mismo jugador");
    }


    @Test
    void changeTurn_shouldIncrementTurnAndResetDice() {
        Turn turn = parchis.getTurn();
        int initialPlayer = turn.getCurrentPlayer();

        Color newTurnColor = playResource.changeTurn();
        String turnPlayer = BoardConstants.COLORS.get(turn.getCurrentPlayer());
        assertNotEquals(initialPlayer, turn.getCurrentPlayer(), "El turno debería haber cambiado");
        assertEquals(BoardConstants.COLORS.get(turn.getCurrentPlayer()), turnPlayer, "El color del nuevo turno no es el esperado");
        assertEquals(0, parchis.getDice().getValue(), "El valor del dado debería haber sido reseteado a 0");
    }

    @Test
    void allPiecesInHome_whenAllPiecesInHome_shouldReturnTrue() {
        Turn turn = parchis.getTurn();
        turn.setCurrentPlayer(0);

        Home home = parchis.getBoard().getHomes()[0];

        assertTrue(playResource.allPiecesInHome(), "Todas las piezas deberían estar en home");
    }

    @Test
    void move_whenInvalidMove_shouldReturnFalseAndNotMovePiece() {
        Turn turn = parchis.getTurn();
        turn.setCurrentPlayer(0);

        Player player = parchis.getCurrentPlayer();
        List<Integer> path = player.getPath();

        Home home =  parchis.getBoard().getHomes()[turn.getCurrentPlayer()];
        Piece piece = home.getPieces().get(0);
        // Establecer un valor de dado que no permita un movimiento válido
        parchis.getDice().setValue(2);

        boolean result = playResource.move(piece);

        assertFalse(result, "No debería ser posible mover la pieza esta en casa");
        assertTrue(home.getPieces().contains(piece), "La pieza debería permanecer en home si el movimiento no fue válido");
    }

    @Test
    void move_whenInvalidMove_shouldReturnFalseColorsNotMatch() {
        Turn turn = parchis.getTurn();
        turn.setCurrentPlayer(0);

        Player player = parchis.getCurrentPlayer();
        List<Integer> path = player.getPath();

        Home home =  parchis.getBoard().getHomes()[turn.getCurrentPlayer()];
        Piece piece = home.getPieces().get(0);
        piece.setColor("blue");
        parchis.getDice().setValue(4);

        boolean result = playResource.move(piece);

        assertFalse(result, "No debería ser posible mover la pieza es de otro color");
    }

    @Test
    void canMove_whenAllPiecesInHome_shouldReturnFalse() {
        Turn turn = parchis.getTurn();
        turn.setCurrentPlayer(0);

        Dice dice = parchis.getDice();
        dice.setValue(5);

        boolean result = playResource.canMove();

        assertFalse(result, "No se debería poder mover ninguna pieza si todas están en home");
    }

    @Test
    void canMove_whenPiecesCanMove_shouldReturnTrue() {
        Dice dice = parchis.getDice();
        dice.setValue(6);
        Piece piece = playResource.exitPiece();

        assertTrue(playResource.canMove(), "Debería ser posible mover la pieza");
    }

    @Test
    void isValidMoveInFinalTrack_whenValidMove_shouldReturnTrue() {

        boolean result = playResource.isValidMoveInFinalTrack(0, 10);

        assertTrue(result, "El movimiento debería ser válido en la pista final");
    }
    @Test
    void isValidMoveInFinalTrack_whenValidMove_shouldReturnFalse() {

        boolean result = playResource.isValidMoveInFinalTrack(4, 20);

        assertFalse(result, "El movimiento no debería ser válido en la pista final");
    }

    @Test
    void isValidMove_whenValidMove_shouldReturnTrue() {
        Player player = parchis.getCurrentPlayer();
        List<Integer> path = player.getPath();
        Piece piece =  playResource.exitPiece();
        assertTrue(playResource.isValidMove(piece, 5, path), "Debería ser un movimiento válido");
    }

    @Test
    void move_whenValidMove_shouldMovePiece() {
        Turn turn = parchis.getTurn();
        turn.setCurrentPlayer(0);

        Player player = parchis.getCurrentPlayer();
        List<Integer> path = player.getPath();
        Piece piece = playResource.exitPiece();
        parchis.getDice().setValue(6);

        assertTrue(playResource.move(piece));
    }

    @Test
    void capturePiece_whenNoCaptureCondition_shouldReturnFalse() {
        playResource.exitPiece(); // Mover una pieza para estar en una posición donde no haya captura

        boolean result = playResource.capturePiece();

        assertFalse(result, "No debería haber captura y por lo tanto no se debería poder mover");
    }


    @Test
    public void testMoveCircuitToFinalTrack() {
        Turn turn = parchis.getTurn();
        turn.setCurrentPlayer(0);

        Player player = parchis.getCurrentPlayer();
        List<Integer> path = player.getPath();
        Piece piece = playResource.exitPiece();
        parchis.getDice().setValue(6);
        Square square = parchis.getBoard().getSquareFromValue(155);
        square.putPiece(piece);
        assertTrue(playResource.move(piece));
    }

    @Test
    public void testMoveInFinalTrackWithNotValidNumber() {
        Turn turn = parchis.getTurn();
        turn.setCurrentPlayer(0);

        Player player = parchis.getCurrentPlayer();
        List<Integer> path = player.getPath();
        Piece piece = playResource.exitPiece();
        parchis.getDice().setValue(36);
        Square square = parchis.getBoard().getSquareFromValue(155);
        square.putPiece(piece);
        assertFalse(playResource.move(piece));
    }

    @Test
    public void testExitFull() {
        playResource.exitPiece();
        playResource.exitPiece();
        parchis.getDice().setValue(5);
        assertFalse(playResource.mustExitPiece());
    }
    @Test
    public void canMove() {
        Piece piece = playResource.exitPiece();
        Piece piece2 = playResource.exitPiece();
        Player player = parchis.getCurrentPlayer();
        List<Integer> path = player.getPath();
        playResource.movePiece(piece,17,path);
        playResource.movePiece(piece2,17,path);
        parchis.getTurn().setCurrentPlayer(3);
        playResource.exitPiece();
        playResource.exitPiece();
        parchis.getDice().setValue(5);
        assertFalse(playResource.canMove());
    }

    @Test
    public void eatPieceTest() {
        Piece piece = playResource.exitPiece();
        Player player = parchis.getCurrentPlayer();
        List<Integer> path = player.getPath();
        playResource.movePiece(piece,2,path);
        playResource.changeTurn();
        Piece pieceBlue = playResource.exitPiece();
        parchis.getDice().setValue(18);
        assertTrue(playResource.move(pieceBlue));
        assertTrue(parchis.isCapture());

    }
    @Test
    public void testMustExitWithHomeEmpty() {
        Piece piece = playResource.exitPiece();
        Piece piece2 = playResource.exitPiece();
        Player player = parchis.getCurrentPlayer();
        List<Integer> path = player.getPath();
        playResource.movePiece(piece,5,path);
        playResource.movePiece(piece2,5,path);
        playResource.exitPiece();
        playResource.exitPiece();
        parchis.getDice().setValue(5);
        assertFalse(playResource.mustExitPiece());
    }
    @Test
    public void testMoveInFinalTrack() {
        Turn turn = parchis.getTurn();
        turn.setCurrentPlayer(0);

        Player player = parchis.getCurrentPlayer();
        List<Integer> path = player.getPath();
        Piece piece = playResource.exitPiece();
        parchis.getDice().setValue(2);
        Square square = parchis.getBoard().getSquareFromValue(141);
        square.putPiece(piece);
        assertTrue(playResource.move(piece));
    }

    @Test
    public void testMoveInFinalTrackWithBound() {
        Turn turn = parchis.getTurn();
        turn.setCurrentPlayer(0);

        Player player = parchis.getCurrentPlayer();
        List<Integer> path = player.getPath();
        Piece piece = playResource.exitPiece();
        parchis.getDice().setValue(10);
        Square square = parchis.getBoard().getSquareFromValue(141);
        square.putPiece(piece);
        assertTrue(playResource.move(piece));
    }

    @Test
    public void testMoveInFinalTrackArriveGoal() {
        Turn turn = parchis.getTurn();
        turn.setCurrentPlayer(0);

        Player player = parchis.getCurrentPlayer();
        List<Integer> path = player.getPath();
        Piece piece = playResource.exitPiece();
        parchis.getDice().setValue(4);
        Square square = parchis.getBoard().getSquareFromValue(141);
        square.putPiece(piece);
        assertTrue(playResource.move(piece));
        assertTrue(parchis.isArriveGoal());
    }

    @Test
    void capturePiece_whenCaptureConditionMet_shouldReturnTrue() {
        playResource.exitPiece();
        parchis.setCapture(true); // Simular una captura previa

        boolean result = playResource.capturePiece();

        assertTrue(result, "Debería haber captura y poder mover");
        assertEquals(20, parchis.getDice().getValue(), "El valor del dado debería haber sido reseteado a 0");
        assertFalse(parchis.isCapture(), "El estado de captura debería haber sido reseteado");
    }

    @Test
    void arriveGoal_whenNoArriveGoalCondition_shouldReturnFalse() {
        playResource.exitPiece(); // Mover una pieza para estar en una posición donde no haya llegada a la meta

        boolean result = playResource.arriveGoal();

        assertFalse(result, "No debería haber llegada a la meta y por lo tanto no se debería poder mover");
    }


    @Test
    void arriveGoal_whenArriveGoalConditionMet_shouldReturnTrue() {
        playResource.exitPiece();
        parchis.setArriveGoal(true); // Simular llegada a la meta

        boolean result = playResource.arriveGoal();

        assertTrue(result, "Debería haber llegada a la meta y poder mover");
        assertEquals(10, parchis.getDice().getValue(), "El valor del dado debería haber sido reseteado a 0");
        assertFalse(parchis.isArriveGoal(), "El estado de llegada a la meta debería haber sido reseteado");
    }

}
