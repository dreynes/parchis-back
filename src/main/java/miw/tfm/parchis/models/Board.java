package miw.tfm.parchis.models;
import java.util.List;

public class Board {

    private int nCols = 17;
    private int nRows = 17;

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
        }

        assignSquares(BoardConstants.SQUARE_SAFE_VALUES, SquareSafe.class, "light-grey", "SquareSafe", this.board, this.circuit);
        assignSquares(BoardConstants.SQUARE_EXIT_RED, SquareExit.class, "red", "SquareExit", this.board, this.circuit);
        assignSquares(BoardConstants.SQUARE_EXIT_BLUE, SquareExit.class, "blue", "SquareExit", this.board, this.circuit);
        assignSquares(BoardConstants.SQUARE_EXIT_YELLOW, SquareExit.class, "yellow", "SquareExit", this.board, this.circuit);
        assignSquares(BoardConstants.SQUARE_EXIT_GREEN, SquareExit.class, "green", "SquareExit", this.board, this.circuit);
        assignSquares(BoardConstants.SQUARE_FINAL_TRACK_VALUES_RED, SquareSafe.class, "red", "FinalTrack", this.board, this.finalTracks[0]);
        assignSquares(BoardConstants.SQUARE_FINAL_TRACK_VALUES_BLUE, SquareSafe.class, "blue", "FinalTrack", this.board, this.finalTracks[1]);
        assignSquares(BoardConstants.SQUARE_FINAL_TRACK_VALUES_YELLOW, SquareSafe.class, "yellow", "FinalTrack", this.board, this.finalTracks[2]);
        assignSquares(BoardConstants.SQUARE_FINAL_TRACK_VALUES_GREEN, SquareSafe.class, "green", "FinalTrack", this.board, this.finalTracks[3]);
        assignSquares(BoardConstants.SQUARE_HOME_VALUES_RED, Square.class, "red", "HomeSquare", this.board, this.homes[0]);
        assignSquares(BoardConstants.SQUARE_HOME_VALUES_BLUE, Square.class, "blue", "HomeSquare", this.board, this.homes[1]);
        assignSquares(BoardConstants.SQUARE_HOME_VALUES_YELLOW, Square.class, "yellow", "HomeSquare", this.board, this.homes[2]);
        assignSquares(BoardConstants.SQUARE_HOME_VALUES_GREEN, Square.class, "green", "HomeSquare", this.board, this.homes[3]);
        assignSquares(BoardConstants.SQUARE_BLACK_BORDER, Square.class, "black", "Border", this.board, null);

        this.board[8][8] = new Goal(145);

        fillRemainingSquares(this.board);
    }

    private Coordinate getPositionFromValue(int value) {
        int index = value - 1;
        int col = index / nCols;
        int row = index % nRows;
        return new Coordinate(row, col);
    }

    private void assignSquares(List<Integer> values, Class<? extends Square> squareClass, String color, String type, Square[][] board, Object section) {
        for (Integer value : values) {
            Coordinate position = getPositionFromValue(value);
            try {
                Square square = squareClass.getDeclaredConstructor(int.class, String.class, String.class).newInstance(value, color, type);
                board[position.getRow()][position.getCol()] = square;
                if (section instanceof Circuit) {
                    ((Circuit) section).addSquare(square);
                } else if (section instanceof Home) {
                    ((Home) section).addSquare(square);
                } else if (section instanceof FinalTrack) {
                    ((FinalTrack) section).addSquare((SquareSafe) square);
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
                    Square square = new Square(count++, "white", "Square");
                    board[i][j] = square;
                    this.circuit.addSquare(square);
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

    public void setCircuit(Circuit circuit) {
    }

    public void setBoard(Square[][] squares) {
    }

    public void setHomes(Home[] homes) {
    }

    public void setFinalTracks(FinalTrack[] finalTracks) {
    }


}
