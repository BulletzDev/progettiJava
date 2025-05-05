package it.proietto.battleShips.ships;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import lombok.Data;

@Data
public class ShipGame {
    private Ships playerField;
    private Ships computerField;
    private List<String> potentialTargets = new ArrayList<>();

    public ShipGame() {
        playerField = new Ships();
        computerField = new Ships();
        computerField.addRandom();
    }

    public void placePlayerShip(int posX, int posy, int length, boolean vertical) throws InvalidPositionException {
        playerField.addShips(posX, posy, length, vertical);
    }

    /**
     * Randomizes all the boat on the field for a player
     */
    public void random() {
        playerField.addRandom();
    }

    /**
     * @param posX indicates the row that you are hitting
     * @param posy indicates the column that you are hitting
     * @return 0 if the player hit and didn't win, 1 if hit ship sunk and won, 2 if
     *         hit and the ship sunk and didn't win,3 if didn't hit
     * @throws AlreadyHitException if the position has already been hit
     */    public int playerMove(int posX, int posy) throws AlreadyHitException {
        return computerField.hitBoat(posX, posy);
    }

    public void emptyField() {
        playerField = new Ships();
    }

    /**
     *
     * @return List<Integer> containing the posX, the posY and an integer from {@link Ships#hitBoat(int, int)}
     */
    public List<Integer> computerMove() {
        Random rand = new Random();
        int posX, posY, result;
        List<Integer> resultList = new ArrayList<>();

        if (!potentialTargets.isEmpty()) {
            String target = potentialTargets.remove(0);
            posX = Integer.parseInt(target.substring(0, 1));
            posY = Integer.parseInt(target.substring(1, 2));
        } else {
            posX = rand.nextInt(10);
            posY = rand.nextInt(10);
        }

        try {
            result = playerField.hitBoat(posX, posY);
            resultList.add(posX);
            resultList.add(posY);
            resultList.add(result);

            if (result == 0 || result == 2) {
                addAdjacentPositions(posX, posY);
            }

            return resultList;
        } catch (AlreadyHitException ignored) {
            return computerMove();
        }
    }

    /**
     * Adds adjacent positions to the potential targets list.
     *
     * @param posX The row of the hit position.
     * @param posY The column of the hit position.
     */
    private void addAdjacentPositions(int posX, int posY) {
        if (posX > 0) potentialTargets.add("" + (posX - 1) + posY);
        if (posX < 9) potentialTargets.add("" + (posX + 1) + posY);
        if (posY > 0) potentialTargets.add("" + posX + (posY - 1));
        if (posY < 9) potentialTargets.add("" + posX + (posY + 1));
    }

}
