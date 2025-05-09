package it.proietto.battleShips;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RestController;

import it.proietto.battleShips.ships.ShipGame;
import it.proietto.battleShips.ships.Ships;

@RestController
public class BattleshipController {

    ShipGame game = new ShipGame();

    @GetMapping("/popola-griglie")
    public Map<String, Ships> popolaGriglie() {
        Map<String, Ships> griglie = new HashMap<>();
        griglie.put("player", generaPosizioniCasualiPlayer());
        griglie.put("computer", game.getComputerField());
        return griglie;
    }

    private Ships generaPosizioniCasualiPlayer() {
        game.getPlayerField().clearAll();
        game.random();
        return game.getPlayerField();
    }
}
