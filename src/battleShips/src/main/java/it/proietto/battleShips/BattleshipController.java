package it.proietto.battleShips;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import it.proietto.battleShips.ships.AttackResponse;
import it.proietto.battleShips.ships.AlreadyHitException;
import it.proietto.battleShips.ships.FieldNotInilializedException;
import it.proietto.battleShips.ships.ShipGame;
import it.proietto.battleShips.ships.Ships;

@RestController
public class BattleshipController {
    
    ShipGame game = new ShipGame();

    @GetMapping("/popGrid")
    public Map<String, Ships> popgrid() {
        Map<String, Ships> griglie = new HashMap<>();
        griglie.put("player", genPosPlayer());
        griglie.put("computer", genPosComputer().maskedForPlayer());
        return griglie;
    }

    @GetMapping("/getGrid")
    public Map<String, Ships> getGrid() {
        Map<String, Ships> griglie = new HashMap<>();
        griglie.put("player", game.getPlayerField());
        griglie.put("computer", game.getComputerField().maskedForPlayer());
        return griglie;
    }

    @GetMapping("/attack/{index}")
    public AttackResponse attack(@PathVariable("index") int index) {

        int x = index / 10;
        int y = index % 10;
        AttackResponse response = new AttackResponse();

        try {
            response.setPlayerResult(game.playerMove(x, y));
        } catch (AlreadyHitException e) {
            response.setPlayerResult(-2);
            return response;
        } catch (FieldNotInilializedException e) {
            response.setPlayerResult(-1);
            return response;
        }
        List<Integer> cMove = game.computerMove();
        response.setComputerResult(cMove);
        return response;
    }

    @GetMapping("/checkWin")
    public int checkWin() {
        return game.checkWin();
    }

    @DeleteMapping("/restartGame")
    public void restartgame() {
        game = new ShipGame();
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
