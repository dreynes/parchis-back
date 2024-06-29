package miw.tfm.parchis.services;

import miw.tfm.parchis.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayResource {


    private final SessionState sessionState;

    @Autowired
    public PlayResource(SessionState sessionState) {
        this.sessionState = sessionState;
    }

    public Integer rollDice() {
        Dice dice = sessionState.getParchis().getDice();
        if (dice.getValue() == 0)
            return dice.roll();
        else {
            return dice.getValue();
        }
    }

    public boolean mustExitPiece() {
        int turnValue = this.sessionState.getParchis().getTurn().getCurrentPlayer();
        Home home = this.sessionState.getParchis().getBoard().getHomes()[turnValue];
        int diceValue = this.sessionState.getParchis().getDice().getValue();
        Circuit circuit = this.sessionState.getParchis().getBoard().getCircuit();
        SquareExit squareExit = circuit.getExitSquare(turnValue);
        if (squareExit.isFull())
            if (squareExit.getPieces().get(0).getColor().equals(squareExit.getPieces().get(0).getColor()))
                return false;
        boolean res = home.getPieces().size() > 0 && diceValue == 5;
        if (res) {
            this.sessionState.getParchis().getDice().setValue(0);
        }
        return res;
    }


    public void exitPiece() {
        int turnValue = this.sessionState.getParchis().getTurn().getCurrentPlayer();
        Home home = this.sessionState.getParchis().getBoard().getHomes()[turnValue];
        Piece piece = home.exitPiece();
        Circuit circuit = this.sessionState.getParchis().getBoard().getCircuit();
        SquareExit squareExit = circuit.getExitSquare(turnValue);
        squareExit.putPiece(piece);
    }

    public Color changeTurn() {
        Turn turn = this.sessionState.getParchis().getTurn();
        this.sessionState.getParchis().getDice().setValue(0);
        turn.nextTurn();
        return Color.values()[turn.getCurrentPlayer()];
    }

    public boolean allPiecesInHome() {
        int turnValue = this.sessionState.getParchis().getTurn().getCurrentPlayer();
        Home home = this.sessionState.getParchis().getBoard().getHomes()[turnValue];
        return home.isFull();
    }

    public Boolean canMove() {
        int diceValue = this.sessionState.getParchis().getDice().getValue();
        if (allPiecesInHome()) {
            this.sessionState.getParchis().getDice().setValue(0);
            return false;
        }
        Player player = this.sessionState.getParchis().getCurrentPlayer();
        List<Integer> path = player.getPath();
        for (Piece piece : player.getPieces()) {
            if (isValidMove(piece, diceValue, path))
                return true;
        }
        this.sessionState.getParchis().getDice().setValue(0);
        return false;
    }

    public Boolean isValidMoveInFinalTrack(int initialPosition, int diceValue) {
        int turnValue = this.sessionState.getParchis().getTurn().getCurrentPlayer();
        FinalTrack finalTrack = this.sessionState.getParchis().getBoard().getFinalTracks()[turnValue];
        List<SquareSafe> squares = finalTrack.getSquares();
        int finalTrackSize = squares.size();
        if (initialPosition != 0) {
            Square square = this.sessionState.getParchis().getBoard().getSquareFromValue(initialPosition);
            initialPosition = squares.indexOf(square);
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
        if (piece.getPosition() == 0)
            return false;
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
            Square square = this.sessionState.getParchis().getBoard().getSquareFromValue(squareValue);
            if (square.hasBlockade()) {
                return false;
            }
        }
        Square square = this.sessionState.getParchis().getBoard().getSquareFromValue(positionInitial + diceValue);
        if (!square.canPutPiece()) {
            return false;
        }
        return true;
    }

    public Boolean move(Piece piece) {
        int diceValue = this.sessionState.getParchis().getDice().getValue();
        Player player = this.sessionState.getParchis().getCurrentPlayer();
        List<Integer> path = player.getPath();
        if (!player.getColor().equals(piece.getColor())) {
            return false;
        }
        if (!isValidMove(piece, diceValue, path)) {
            return false;
        } else {
            movePiece(piece, diceValue, path);
            this.sessionState.getParchis().getDice().setValue(0);
            return true;
        }
    }


    public void movePiece(Piece piece, int diceValue, List<Integer> path) {
        Player player = this.sessionState.getParchis().getCurrentPlayer();
        this.sessionState.getParchis().getCurrentPlayer();
        int positionInitial = path.indexOf(piece.getPosition());
        if (positionInitial == -1) {
            FinalTrack finalTrack = this.sessionState.getParchis().getBoard().getFinalTracks()[this.sessionState.getParchis().getTurn().getCurrentPlayer()];
            List<SquareSafe> squares = finalTrack.getSquares();
            Square square = this.sessionState.getParchis().getBoard().getSquareFromValue(piece.getPosition());
            int initialPosition = squares.indexOf(square);
            moveInFinalTrack(piece, initialPosition, diceValue);
        } else {
            Square squareInitial = this.sessionState.getParchis().getBoard().getSquareFromValue(path.get(positionInitial));
            squareInitial.removePiece(piece);
            if (positionInitial + diceValue >= path.size()) {
                int extra = positionInitial + diceValue - path.size();
                moveInFinalTrack(piece, 0, extra);
            } else {
                int positionFinal = positionInitial + diceValue;
                Square square = this.sessionState.getParchis().getBoard().getSquareFromValue(path.get(positionFinal));
                if (square.getPieces().size() == 1) {
                    Piece pieceToEat = square.getPieces().get(0);
                    if (!pieceToEat.getColor().equals(piece.getColor())) {
                        if (square.capturePiece()) {
                            this.sessionState.getParchis().setCapture(true);
                            this.sessionState.getParchis().movePieceHome(pieceToEat);
                        }
                    }
                }
                player.getPieces().remove(piece);
                square.putPiece(piece);
                player.getPieces().add(piece);
            }
        }
    }


    private void moveInFinalTrack(Piece piece, int initialPosition, int diceValue) {
        Player player = this.sessionState.getParchis().getCurrentPlayer();
        int turnValue = this.sessionState.getParchis().getTurn().getCurrentPlayer();
        FinalTrack finalTrack = this.sessionState.getParchis().getBoard().getFinalTracks()[turnValue];
        List<SquareSafe> squares = finalTrack.getSquares();
        int finalTrackSize = squares.size();

        int finalPosition = initialPosition + diceValue;
        if (finalPosition == finalTrackSize) {
            player.getPieces().remove(piece);
            squares.get(initialPosition).removePiece(piece);
            this.sessionState.getParchis().putPieceGoal(piece);
        } else {
            if (finalPosition >= finalTrackSize) {
                finalPosition = finalTrackSize - (finalPosition - finalTrackSize);
            }
            player.getPieces().remove(piece);
            squares.get(initialPosition).removePiece(piece);
            squares.get(finalPosition).putPiece(piece);
            player.getPieces().add(piece);
        }
    }

    public Boolean capturePiece() {
        this.sessionState.getParchis().getDice().setValue(20);
        boolean res = this.sessionState.getParchis().isCapture() && canMove();
        if (!res) {
            this.sessionState.getParchis().getDice().setValue(0);
        }
        this.sessionState.getParchis().setCapture(false);
        return res;
    }

    public Boolean arriveGoal() {
        this.sessionState.getParchis().getDice().setValue(10);
        boolean res = this.sessionState.getParchis().isArriveGoal() && canMove();
        if (!res) {
            this.sessionState.getParchis().getDice().setValue(0);
        }
        this.sessionState.getParchis().setArriveGoal(false);
        return res;
    }
}


