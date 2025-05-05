package it.proietto.battleShips.ships;

import lombok.Data;
import lombok.NonNull;

@Data
public class Node {
    @NonNull
    private int posX, posy;
    private boolean hit = false;

}
