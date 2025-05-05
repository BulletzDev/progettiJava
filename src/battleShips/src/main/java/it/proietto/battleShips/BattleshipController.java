package it.proietto.battleShips;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class BattleshipController {

    private Set<Integer> computerShips = new HashSet<>(generaPosizioniCasuali());

    @GetMapping("/popola-griglie")
    public Map<String, List<Integer>> popolaGriglie() {
        Map<String, List<Integer>> griglie = new HashMap<>();
        griglie.put("player", generaPosizioniCasuali());
        griglie.put("computer", new ArrayList<>(computerShips));
        return griglie;
    }

    private static List<Integer> generaPosizioniCasuali() {
        Random random = new Random();
        Set<Integer> posizioni = new HashSet<>();
        while (posizioni.size() < 10) {
            posizioni.add(random.nextInt(100));
        }
        return new ArrayList<>(posizioni);
    }
}
