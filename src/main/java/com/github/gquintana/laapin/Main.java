package com.github.gquintana.laapin;

import com.github.gquintana.laapin.affichage.GrillePanel;
import com.github.gquintana.laapin.affichage.StatsPanel;
import com.github.gquintana.laapin.affichage.ToolBarPanel;
import com.github.gquintana.laapin.moteur.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;

public class Main extends Application {
    private Configuration configuration;
    private Moteur moteur;
    private GrillePanel grillePanel;
    private StatsPanel statsPanel;
    private ToolBarPanel toolBarPanel;

    @Override
    public void init() throws Exception {
        super.init();
        configuration = new Configuration();
        configuration.load();
        if (!getParameters().getRaw().isEmpty()) {
            String configFileName = getParameters().getRaw().get(0);
            try(InputStream inputStream = Resources.openStream(configFileName)) {
                configuration.load(inputStream);
            }
        }
        moteur = new Moteur(configuration, new MoteurListener() {
            @Override
            public void onInitialiser(Grille grille) {
                Platform.runLater(() -> {
                    if (statsPanel!=null) {
                        statsPanel.clearActions();
                    }
                    mettreAJour(grille);
                });
            }

            @Override
            public void onAgir(Grille grille, Lapin lapin, ResultatAction resultatAction) {
                Platform.runLater(() -> {
                    mettreAJour(grille, resultatAction);
                });
            }

            @Override
            public void onArreter(Grille grille) {
                Platform.runLater(() -> {
                    mettreAJour(grille);
                });
            }
        });
        moteur.initialiser();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Laapin");
        grillePanel = new GrillePanel(configuration);
        statsPanel = new StatsPanel();
        HBox centerPane = new HBox(statsPanel, grillePanel);
        toolBarPanel = new ToolBarPanel();
        toolBarPanel.setMoteur(moteur);
        BorderPane rootPane = new BorderPane(centerPane, toolBarPanel, null, null, null);
        Scene scene = new Scene(rootPane);
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(grillePanel.imageLapin);
        primaryStage.setOnCloseRequest(event -> {
            if (moteur != null) {
                moteur.arreter();
            }
        });
        primaryStage.show();
        primaryStage.toFront();
    }

    private void mettreAJour(Grille grille) {
        mettreAJour(grille, null);
    }

    private void mettreAJour(Grille grille, ResultatAction resultatAction) {
        if (grillePanel != null) {
            grillePanel.setGrille(grille);
            grillePanel.repaint();
        }
        if (statsPanel != null) {
            statsPanel.setLapinsList(grille.lapins());
            if (resultatAction != null) {
                statsPanel.addAction(resultatAction);
            }
        }
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