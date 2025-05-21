package it.proietto;

import java.util.ArrayList;
import java.util.List;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AttackResponse {
    private int playerResult; 

    private List<Integer> computerResult; 

    // Costruttori
    public AttackResponse(int playerResult, int[] computerResult) {
        this.playerResult = playerResult;
        this.computerResult = new ArrayList<>();
    }

    // Getters e Setters
    public int getPlayerResult() {
        return playerResult;
    }

    public void setPlayerResult(int playerResult) {
        this.playerResult = playerResult;
    }

    public List<Integer> getComputerResult() {
        return computerResult;
    }

    public void setComputerResult(List<Integer> computerResult) {
        this.computerResult = computerResult;
    }
}
