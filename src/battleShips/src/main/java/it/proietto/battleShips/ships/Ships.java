package it.proietto.battleShips.ships;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Ships {
    private List<Ship> allShips = new ArrayList<>();
    private List<String> alreadyHit = new ArrayList<>();
    private List<String> potentialTargets = new ArrayList<>();

    /**
     * @param posX     indicates the row of the first node of the boat that you are
     *                 hitting
     * @param posy     indicates the column of the first node of the boat that you
     *                 are building
     * @param length   is the length of the ship, how many nodes it is composed of
     * @param vertical a boolean true if vertical, false if horizontal
     * @throws InvalidPositionException if the position is occupied or out of the
     *                                  field
     */
    public void addShips(int posX, int posy, int length, boolean vertical) throws InvalidPositionException {
        for (Ship ship : allShips) {
            for (Node node : ship.getShip()) {
                for (int i = 0; i < length; i++) {
                    int checkX = vertical ? posX : posX + i;
                    int checkY = vertical ? posy + i : posy;

                    if (node.getPosX() == checkX && node.getPosy() == checkY) {
                        throw new InvalidPositionException("On another boat!");
                    }
                }
            }
        }
        if ((vertical && posy + length > 10) || (!vertical && posX + length > 10)) {
            throw new InvalidPositionException("Out of bounds!");
        }

        allShips.add(new Ship(posX, posy, length, vertical));
    }

    /**
     * Randomizes all the boat on the field for a player
     */
    public void addRandom() {
        Random pox = new Random(System.currentTimeMillis());

        int[] length = { 4, 3, 3, 3, 2, 2, 1, 1 };
        for (int h = 0; h < 8; h++) {
            boolean placed = false;
            while (!placed) {
                int posX = pox.nextInt(10);
                int posy = pox.nextInt(10);
                int vert = pox.nextInt(2);
                boolean vertical = vert == 0;
                try {
                    addShips(posX, posy, length[h], vertical);
                    placed = true;
                } catch (InvalidPositionException ignored) {
                }

            }
        }
    }

    /**
     * @param posX indicates the row that you are hitting
     * @param posY indicates the column that you are hitting
     * @return 0 if the player hit and didn't win, 1 if hit ship sunk and won, 2 if
     *         hit and the ship sunk and didn't win,3 if didn't hit
     * @throws AlreadyHitException if the position has already been hit
     */
    public int hitBoat(int posX, int posY) throws AlreadyHitException {
        for (Ship ship : allShips) {
            for (Node node : ship.getShip()) {
                if (posX == node.getPosX() && posY == node.getPosy()) {
                    if (node.isHit()) {
                        throw new AlreadyHitException("Position already hit!");
                    }
                    node.setHit(true);
                    boolean justSunk = ship.checkSunk(); // Know if it just sank now
                    return checkWin(justSunk);
                }
            }
        }
        if (alreadyHit.contains("" + posX + posY)) {
            throw new AlreadyHitException("Position already hit!");
        }
        alreadyHit.add("" + posX + posY);
        return 3;
    }

    /**
     * @return 2 if the player sunk a ship but hasn't won,
     *         1 if the player sunk a ship and won,
     *         0 if the player hasn't won yet.
     */
    public int checkWin(boolean justSunk) {
        boolean allSunk = true;

        for (Ship ship : allShips) {
            if (!ship.isSunk()) {
                allSunk = false;
                break;
            }
        }

        if (allSunk) {
            return 1; // Player won
        }
        if (justSunk) {
            return 2; // Ship sunk this move, but not all
        }
        return 0; // Hit but no ship sunk
    }

    public void clearAll() {
        allShips.clear();
        alreadyHit.clear();
        potentialTargets.clear();
    }

    public boolean isEmpty() {
        return allShips.isEmpty();
    }

    public Ships maskedForPlayer() {
        Ships masked = new Ships();
        masked.setAlreadyHit(new ArrayList<>(this.alreadyHit));

        List<Ship> maskedShips = new ArrayList<>();
        for (Ship ship : this.allShips) {
            for (Node node : ship.getShip()) {
                if (node.isHit()) {
                    Ship maskedShip = new Ship();
                    List<Node> singleHit = new ArrayList<>();
                    singleHit.add(new Node(node.getPosX(), node.getPosy(), true));
                    maskedShip.setShip(singleHit);
                    maskedShip.setSunk(false); // You can optionally check if ship is sunk
                    maskedShips.add(maskedShip);
                }
            }
        }

        masked.setAllShips(maskedShips);
        return masked;
    }
}
