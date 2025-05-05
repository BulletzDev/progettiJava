package it.proietto.battleShips.ships;

public class AlreadyHitException extends Exception {

    public AlreadyHitException(String errorMessage) {
        super(errorMessage);
    }
}
