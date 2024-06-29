package miw.tfm.parchis.models;

public class Turn {

    private int currentPlayer;
    private int numberOfPlayers;

    public Turn(int numberOfPlayers) {
        if (numberOfPlayers < 2 && numberOfPlayers < 4) {
            throw new IllegalArgumentException("El número de jugadores debe ser al menos 2 y máximo 4");
        }
        this.numberOfPlayers = numberOfPlayers;
        this.currentPlayer = 0;
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public void nextTurn() {
        currentPlayer = (currentPlayer + 1) % numberOfPlayers;
    }

    public void setCurrentPlayer(int currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }
}