package miw.tfm.parchis.models;

import java.util.Collections;
import java.util.List;

public class Board {

    private int nCols = 17;
    private int nRows = 17;
    private Goal goal;
    private Square[][] board = new Square[nRows][nCols];
    private Circuit circuit;
    private Home[] homes;
    private FinalTrack[] finalTracks;

    public Board() {
        this.circuit = new Circuit();
        this.homes = new Home[4];
        this.finalTracks = new FinalTrack[4];
        for (int i = 0; i < 4; i++) {
            this.homes[i] = new Home(BoardConstants.COLORS.get(i));
            this.finalTracks[i] = new FinalTrack();
            assignSquares(Collections.singletonList(BoardConstants.SQUARE_EXIT.get(i)), SquareExit.class, BoardConstants.COLORS.get(i), "SquareExit", this.circuit);
        }
        assignSquares(BoardConstants.SQUARE_CIRCUIT, Square.class, "white", "Square", this.circuit);
        assignSquares(BoardConstants.SQUARE_SAFE_VALUES, SquareSafe.class, "light-grey", "SquareSafe", this.circuit);
        assignSquares(BoardConstants.SQUARE_FINAL_TRACK_VALUES_RED, SquareSafe.class, "red", "FinalTrack",this.finalTracks[0]);
        assignSquares(BoardConstants.SQUARE_FINAL_TRACK_VALUES_BLUE, SquareSafe.class, "blue", "FinalTrack", this.finalTracks[1]);
        assignSquares(BoardConstants.SQUARE_FINAL_TRACK_VALUES_YELLOW, SquareSafe.class, "yellow", "FinalTrack",this.finalTracks[2]);
        assignSquares(BoardConstants.SQUARE_FINAL_TRACK_VALUES_GREEN, SquareSafe.class, "green", "FinalTrack", this.finalTracks[3]);
        assignSquares(BoardConstants.SQUARE_HOME_VALUES_RED, Square.class, "red", "HomeSquare", this.homes[0]);
        assignSquares(BoardConstants.SQUARE_HOME_VALUES_BLUE, Square.class, "blue", "HomeSquare", this.homes[1]);
        assignSquares(BoardConstants.SQUARE_HOME_VALUES_YELLOW, Square.class, "yellow", "HomeSquare", this.homes[2]);
        assignSquares(BoardConstants.SQUARE_HOME_VALUES_GREEN, Square.class, "green", "HomeSquare", this.homes[3]);

        this.goal = new Goal(145);
        this.board[8][8] = this.goal;

        fillRemainingSquares(this.board);
    }

    public Coordinate getPositionFromValue(int value) {
        int index = value - 1;
        int col = index / nCols;
        int row = index % nRows;
        return new Coordinate(row,col);
    }
    public Square getSquareFromValue(int value) {
        Coordinate coordinate = this.getPositionFromValue(value);
        return this.board[coordinate.getRow()][coordinate.getCol()];
    }

    public void setSquareFromValue(Square square) {
        Coordinate coordinate = this.getPositionFromValue(square.getValue());
        this.board[coordinate.getRow()][coordinate.getCol()] = square;
    }
    private void assignSquares(List<Integer> values, Class<? extends Square> squareClass, String color, String type, Object section) {
        for (Integer value : values) {
            Coordinate position = getPositionFromValue(value);
            try {
                Square square = squareClass.getDeclaredConstructor(int.class, String.class, String.class).newInstance(value, color, type);
                this.board[position.getRow()][position.getCol()] = square;
                if (section instanceof Home) {
                    ((Home) section).addSquare(square);
                } else if (section instanceof FinalTrack) {
                    ((FinalTrack) section).addSquare((SquareSafe) square);
                } else if (section instanceof Circuit) {
                    ((Circuit) section).getSquares().add((SquareSafe) square);
                }
            } catch (Exception e) {
            }
        }
    }

    private void fillRemainingSquares(Square[][] board) {
        int count = 1;
        for (int j = 0; j < nCols; j++) {
            for (int i = 0; i < nRows; i++) {
                if (board[i][j] == null) {
                    Square square = new Square(count++, "black", "Border");
                    board[i][j] = square;
                } else {
                    count++;
                }
            }
        }
    }

    public Square[][] getBoard() {
        return board;
    }

    public Circuit getCircuit() {
        return circuit;
    }

    public Home[] getHomes() {
        return homes;
    }

    public FinalTrack[] getFinalTracks() {
        return finalTracks;
    }



    public void movePieceHome(Piece piece) {
        for(Home home : this.homes){
            if(home.getColor().equals(piece.getColor())){
                home.putPiece(piece);
            }
        }
    }

    public Goal getGoal() {
        return goal;
    }

    public void setGoal(Goal goal) {
        this.goal = goal;
    }

    public void putPieceInGoal(Piece piece) {
        this.board[8][8].putPiece(piece);
    }
}
