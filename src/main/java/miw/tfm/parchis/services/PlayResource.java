package miw.tfm.parchis.services;

import miw.tfm.parchis.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayResource {


    private final GameState gameState;

    @Autowired
    public PlayResource(GameState gameState) {
        this.gameState = gameState;
    }

    public Integer rollDice() {
        Dice dice = gameState.getParchis().getDice();
        if (dice.getValue() == 0)
            return dice.roll();
        else {
            return dice.getValue();
        }
    }

    public boolean mustExitPiece() {
        int turnValue = this.gameState.getParchis().getTurn().getCurrentPlayer();
        Home home = this.gameState.getParchis().getBoard().getHomes()[turnValue];
        int diceValue = this.gameState.getParchis().getDice().getValue();
        Circuit circuit = this.gameState.getParchis().getBoard().getCircuit();
        SquareExit squareExit = circuit.getExitSquare(turnValue);
        if (squareExit.isFull()) {
            if (squareExit.getPieces().get(0).getColor().equals(squareExit.getPieces().get(1).getColor())){
                  return false;
            }
        }
        boolean res = home.getPieces().size() > 0 && diceValue == 5;
        if (res) {
            this.gameState.getParchis().getDice().setValue(0);
        }
        return res;
    }


    public Piece exitPiece() {
        int turnValue = this.gameState.getParchis().getTurn().getCurrentPlayer();
        Home home = this.gameState.getParchis().getBoard().getHomes()[turnValue];
        Square square = home.exitPiece();
        Circuit circuit = this.gameState.getParchis().getBoard().getCircuit();
        SquareExit squareExit = circuit.getExitSquare(turnValue);
        Piece piece = square.getPieces().get(0);
        Player player = this.gameState.getParchis().getCurrentPlayer();
        player.getPieces().remove(piece);
        squareExit.putPiece(square.getPieces().get(0));
        square.getPieces().remove(piece);
        player.getPieces().add(piece);
        this.gameState.getParchis().getBoard().setSquareFromValue(squareExit);
        this.gameState.getParchis().getBoard().setSquareFromValue(square);

        return piece;
    }

    public Color changeTurn() {
        Turn turn = this.gameState.getParchis().getTurn();
        this.gameState.getParchis().getDice().setValue(0);
        turn.nextTurn();
        return Color.values()[turn.getCurrentPlayer()];
    }
    public Color getTurn() {
        Turn turn = this.gameState.getParchis().getTurn();
        return Color.values()[turn.getCurrentPlayer()];
    }

    public boolean allPiecesInHome() {
        int turnValue = this.gameState.getParchis().getTurn().getCurrentPlayer();
        Home home = this.gameState.getParchis().getBoard().getHomes()[turnValue];
        return home.isFull();
    }

    public Boolean canMove() {
        int diceValue = this.gameState.getParchis().getDice().getValue();
        if (allPiecesInHome()) {
            this.gameState.getParchis().getDice().setValue(0);
            return false;
        }
        Player player = this.gameState.getParchis().getCurrentPlayer();
        List<Integer> path = player.getPath();
        for (Piece piece : player.getPieces()) {
            if (isValidMove(piece, diceValue, path))
                return true;
        }
        this.gameState.getParchis().getDice().setValue(0);
        return false;
    }

    public Boolean isValidMoveInFinalTrack(int initialPosition, int diceValue) {
        int turnValue = this.gameState.getParchis().getTurn().getCurrentPlayer();
        FinalTrack finalTrack = this.gameState.getParchis().getBoard().getFinalTracks()[turnValue];
        List<SquareSafe> squares = finalTrack.getSquares();
        int finalTrackSize = squares.size();

        if (initialPosition != 0) {
            Square square = this.gameState.getParchis().getBoard().getSquareFromValue(initialPosition);
            initialPosition = squares.indexOf(square);
            if(initialPosition==-1){
                return false;
            }
        }
        int finalPosition = initialPosition + diceValue;
        if (finalPosition >= finalTrackSize) {
            finalPosition = finalTrackSize - (finalPosition - finalTrackSize + 1);
        }
        if(finalPosition < 0) {
            return false;
        }
        return squares.get(finalPosition).canPutPiece();
    }

    public Boolean isValidMove(Piece piece, int diceValue, List<Integer> path) {
        int positionInitial = path.indexOf(piece.getPosition());
        if (piece.getPosition() == 0){
            return false;
        }
        if (positionInitial == -1) {
            return isValidMoveInFinalTrack(piece.getPosition(), diceValue);
        }
        if (positionInitial + diceValue >= path.size()) {
            int extra = positionInitial + diceValue - path.size();
            diceValue = path.size() - positionInitial - 1;
            if (!isValidMoveInFinalTrack(0, extra)) {
                return false;
            }
        }
        for (int i = 1; i <= diceValue; i++) {
            int squareValue = path.get(positionInitial + i);
            Square square = this.gameState.getParchis().getBoard().getSquareFromValue(squareValue);
            if (square.hasBlockade()) {
                return false;
            }
        }
        Square square = this.gameState.getParchis().getBoard().getSquareFromValue(positionInitial + diceValue);
        if (!square.canPutPiece()) {
            return false;
        }
        return true;
    }

    public Boolean move(Piece piece) {
        int diceValue = this.gameState.getParchis().getDice().getValue();
        Player player = this.gameState.getParchis().getCurrentPlayer();
        player.getPieces().remove(piece);
        List<Integer> path = player.getPath();
        if (!player.getColor().equals(piece.getColor())) {
            return false;
        }
        if (!isValidMove(piece, diceValue, path)) {
            return false;
        } else {
            movePiece(piece, diceValue, path);
            this.gameState.getParchis().getDice().setValue(0);
            player.getPieces().add(piece);
            return true;
        }
    }


    public void movePiece(Piece piece, int diceValue, List<Integer> path) {
        Player player = this.gameState.getParchis().getCurrentPlayer();
        this.gameState.getParchis().getCurrentPlayer();
        int positionInitial = path.indexOf(piece.getPosition());
        if (positionInitial == -1) {
            FinalTrack finalTrack = this.gameState.getParchis().getBoard().getFinalTracks()[this.gameState.getParchis().getTurn().getCurrentPlayer()];
            List<SquareSafe> squares = finalTrack.getSquares();
            Square square = this.gameState.getParchis().getBoard().getSquareFromValue(piece.getPosition());
            int initialPosition = squares.indexOf(square);
            moveInFinalTrack(piece, initialPosition, diceValue);
        } else {
            Square squareInitial = this.gameState.getParchis().getBoard().getSquareFromValue(path.get(positionInitial));
            squareInitial.removePiece(piece);
            this.gameState.getParchis().getBoard().setSquareFromValue(squareInitial);
            if (positionInitial + diceValue >= path.size()) {
                int extra = positionInitial + diceValue - path.size();
                moveInFinalTrack(piece, 0, extra);
            } else {
                int positionFinal = positionInitial + diceValue;
                Square square = this.gameState.getParchis().getBoard().getSquareFromValue(path.get(positionFinal));
                if (square.getPieces().size() == 1) {
                    Piece pieceToEat = square.getPieces().get(0);
                    if (!pieceToEat.getColor().equals(piece.getColor())) {
                        if (square.capturePiece()) {
                            this.gameState.getParchis().setCapture(true);
                            this.gameState.getParchis().movePieceHome(pieceToEat);
                        }
                    }
                }
                player.getPieces().remove(piece);
                square.putPiece(piece);
                this.gameState.getParchis().getBoard().setSquareFromValue(square);
                player.getPieces().add(piece);
            }
        }
    }


    private void moveInFinalTrack(Piece piece, int initialPosition, int diceValue) {
        Player player = this.gameState.getParchis().getCurrentPlayer();
        int turnValue = this.gameState.getParchis().getTurn().getCurrentPlayer();
        FinalTrack finalTrack = this.gameState.getParchis().getBoard().getFinalTracks()[turnValue];
        List<SquareSafe> squares = finalTrack.getSquares();
        int finalTrackSize = squares.size();

        int finalPosition = initialPosition + diceValue;
        if (finalPosition == finalTrackSize) {
            player.getPieces().remove(piece);
            squares.get(initialPosition).removePiece(piece);
            this.gameState.getParchis().putPieceGoal(piece);
            this.gameState.getParchis().getBoard().setSquareFromValue(squares.get(initialPosition));
        } else {
            if (finalPosition >= finalTrackSize) {
                finalPosition = finalTrackSize - (finalPosition - finalTrackSize);
            }
            player.getPieces().remove(piece);
            squares.get(initialPosition).removePiece(piece);
            squares.get(finalPosition).putPiece(piece);
            this.gameState.getParchis().getBoard().setSquareFromValue(squares.get(initialPosition));
            this.gameState.getParchis().getBoard().setSquareFromValue(squares.get(finalPosition));
            player.getPieces().add(piece);
        }
    }

    public Boolean capturePiece() {
        this.gameState.getParchis().getDice().setValue(20);
        boolean res = this.gameState.getParchis().isCapture() && canMove();
        if (!res) {
            this.gameState.getParchis().getDice().setValue(0);
        }
        this.gameState.getParchis().setCapture(false);
        return res;
    }

    public Boolean arriveGoal() {
        this.gameState.getParchis().getDice().setValue(10);
        boolean res = this.gameState.getParchis().isArriveGoal() && canMove();
        if (!res) {
            this.gameState.getParchis().getDice().setValue(0);
        }
        this.gameState.getParchis().setArriveGoal(false);
        return res;
    }
}


