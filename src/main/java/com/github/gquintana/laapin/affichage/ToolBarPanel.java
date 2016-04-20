package com.github.gquintana.laapin.affichage;

import com.github.gquintana.laapin.moteur.Moteur;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ToolBarPanel extends ToolBar {
    private Moteur moteur;
    private final ImageView imagePlay;
    private final ImageView imagePause;
    private final ImageView imageStep;
    private final ImageView imageRewind;

    public ToolBarPanel() {
        imagePlay = createImage("icon-play.png");
        imagePause = createImage("icon-pause.png");
        imageStep = createImage("icon-step.png");
        imageRewind = createImage("icon-rewind.png");
        Button btnPlayPause = createButton(imagePlay);
        btnPlayPause.setOnAction((event) -> {
            if (moteur.estEnMarche()) {
                moteur.stopper();
            } else {
                moteur.demarrer();
            }
            Platform.runLater(() -> {
                btnPlayPause.setGraphic(moteur.estEnMarche() ?
                        imagePause : imagePlay);
            });
        });
        Button btnStep = createButton(imageStep);
        btnStep.setOnAction(event -> {
            moteur.avancer();
        });
        Button btnRewind = createButton(imageRewind);
        btnRewind.setOnAction(event -> { moteur.initialiser(); });
        getItems().addAll(
                btnPlayPause,
                btnStep,
                btnRewind
        );
    }

    private static Button createButton(Node graphic) {
        Button button = new Button();
        button.setGraphic(graphic);
        return button;
    }

    private static ImageView createImage(String resourceName) {
        return new ImageView(new Image(ToolBarPanel.class.getResourceAsStream(resourceName), 32D, 32D, true, true));
    }

    public void setMoteur(Moteur moteur) {
        this.moteur = moteur;
    }
}
