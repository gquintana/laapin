package com.github.gquintana.laapin;

import com.github.gquintana.laapin.moteur.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;

public class Main extends Application {
    private Moteur moteur;
    private GrillePanel grillePanel;
    private Grille grille;


    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Laapin");
        Group root = new Group();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);

        grillePanel = new GrillePanel(grille);
        root.getChildren().add(grillePanel);
        primaryStage.getIcons().add(grillePanel.imageLapin);
        primaryStage.show();
        primaryStage.toFront();
    }

    @Override
    public void init() throws Exception {
        super.init();
        final Configuration configuration = new Configuration();
        configuration.load();
        if (!getParameters().getRaw().isEmpty()) {
            try(FileInputStream inputStream = new FileInputStream(getParameters().getRaw().get(0))) {
                configuration.load(inputStream);
            }
        }
        moteur = new Moteur(configuration, new MoteurListener() {
            @Override
            public void onDemarrer(Grille grille) {
                Main.this.grille = grille;
            }

            @Override
            public void onAgir(Grille grille, Lapin lapin, ResultatAction resultatAction) {
                Platform.runLater(() -> {
                    if (grillePanel != null) {
                        grillePanel.repaint();
                    }
                });
            }

            @Override
            public void onArreter(Grille grille) {

            }
        });
        moteur.demarrer();
    }

    @Override
    public void stop() throws Exception {
        moteur.arreter();
        super.stop();
    }

    public static void main(String[] args) throws IOException {
        launch(Main.class, args);
    }
}