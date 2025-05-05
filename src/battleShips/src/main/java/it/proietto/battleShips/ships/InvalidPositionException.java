package it.proietto.battleShips.ships;

public class InvalidPositionException extends Exception {

    public InvalidPositionException(String errorMessage) {
        super(errorMessage);
    }
}
