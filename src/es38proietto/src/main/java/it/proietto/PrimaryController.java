package it.proietto;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

public class PrimaryController {

    @FXML
    private ProgressBar pb0, pb1, pb2, pb3, pb4;
    @FXML
    private Label resultRace;

    private List<ProgressBar> progressBars = new ArrayList<>();

    @FXML
    public void initialize() {
        progressBars = List.of(pb0, pb1, pb2, pb3, pb4);
        MainRace.controller = this;
    }

    @FXML
    public void beginRace() {
        MainRace.startRace();
    }

    public void moveHorse(int index, int meters) {
        double progress = meters / 100.0;
        if (progress >= 1) {
            progress = 1;
            if (!MainRace.raceStopped) {
                won(index);
            }
        }

        progressBars.get(index).setProgress(progress);
    }

public void won(int index) {
    
    if (!MainRace.raceStopped) {
        MainRace.raceStopped = true; 
        Platform.runLater(() -> {
            resultRace.setText("The Horse " + (index+1) + " won");
        });
    }
}

    @FXML
    public void reInitialize() throws InterruptedException {
        if (!MainRace.raceStopped) {
            MainRace.stopRace();
        }
        for (ProgressBar pb : progressBars) {
            pb.setProgress(0);
        }
        resultRace.setText("");

    }

}
