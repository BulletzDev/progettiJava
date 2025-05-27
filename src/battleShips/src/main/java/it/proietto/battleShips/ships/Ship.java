package it.proietto.battleShips.ships;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Ship {
    private List<Node> ship = new ArrayList<>();
    private boolean isSunk = false;

    public Ship() {
        this.ship = new ArrayList<>();
        this.isSunk = false;
    }

    public Ship(int posx, int posy, int length, boolean vertical) {
        if (vertical) {
            for (int i = 0; i < length; i++) {
                ship.add(new Node(posx, posy + i));
            }
        } else {
            for (int i = 0; i < length; i++) {
                ship.add(new Node(posx + i, posy));
            }
        }
    }

    public boolean checkSunk() {
        if (isSunk)
            return false; // Already sunk, do nothing

        for (Node n : ship) {
            if (!n.isHit()) {
                return false; // Still floating
            }
        }

        isSunk = true; // Just sunk now
        return true;
    }
}
