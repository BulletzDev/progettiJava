package it.proietto.battleShips.ships;

import lombok.Data;
import lombok.NonNull;

@Data
public class Node {
    @NonNull
    private int posX, posy;
    private boolean hit = false;

    public Node(int posX, int posy, boolean hit) {
        this.posX = posX;
        this.posy = posy;
        this.hit = hit;
    }

    public Node(int posX, int posy) {
        this.posX = posX;
        this.posy = posy;
    }

}
