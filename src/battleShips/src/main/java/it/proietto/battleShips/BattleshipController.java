package it.proietto.battleShips;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RestController;

import it.proietto.battleShips.ships.ShipGame;
import it.proietto.battleShips.ships.Ships;

@RestController
public class BattleshipController {

    ShipGame game = new ShipGame();

    @GetMapping("/popGrid")
    public Map<String, Ships> popgrid() {
        Map<String, Ships> griglie = new HashMap<>();
        griglie.put("player", genPosPlayer());
        griglie.put("computer", genPosComputer());
        return griglie;
    }

    @GetMapping("/getGrid")
    public Map<String, Ships> getGrid() {
        Map<String, Ships> griglie = new HashMap<>();
        griglie.put("player", game.getPlayerField());
        griglie.put("computer", game.getComputerField());
        return griglie;
    }

    private Ships genPosPlayer() {
        if (game.getPlayerField().isEmpty()) {
            game.getPlayerField().clearAll();
            game.random();
        }
        return game.getPlayerField();
    }

    private Ships genPosComputer() {
        if (game.getComputerField().isEmpty()) {
            game.getComputerField().clearAll();
            game.random();
        }
        return game.getComputerField();
    }

}
