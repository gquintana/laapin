package com.github.gquintana.laapin.affichage;

import com.github.gquintana.laapin.Configuration;
import com.github.gquintana.laapin.moteur.*;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.IOException;

public class GrillePanel extends Group {
    private final int resolution;
    private final Grille grille;
    private final Canvas canvas;
    private final Image imageFond;
    public final Image imageLapin;
    private final Image imageCarotte;
    private final Image imageRocher;


    public GrillePanel(Grille grille, Configuration configuration) throws IOException {
        this.grille = grille;
        this.resolution = configuration.getInt("grille.resolution", 64);
        int width = grille.taille.x * resolution;
        int height = grille.taille.y * resolution;
        canvas = new Canvas(width, height);
        getChildren().add(canvas);
        imageFond = loadImage("fond");
        imageLapin = loadImage("lapin");
        imageCarotte = loadImage("carotte");
        imageRocher = loadImage("rocher");
    }

    private Image loadImage(String name) {
        return new Image(getClass().getResourceAsStream(name + "-" + resolution + ".png"));
    }

    public void repaint() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        for (int x = 0; x < grille.taille.x; x++) {
            for (int y = 0; y < grille.taille.y; y++) {
                gc.drawImage(imageFond, x * resolution, y * resolution, resolution, resolution);
            }
        }
        for (Lutin lutin : grille.lutins) {
            Image image = null;
            if (lutin instanceof Carotte) {
                image = imageCarotte;
            } else if (lutin instanceof Rocher) {
                image = imageRocher;
            } else if (lutin instanceof Lapin) {
                Lapin lapin = (Lapin) lutin;
                int x0 = lapin.coord.x * resolution;
                int y0 = lapin.coord.y * resolution;
                gc.setStroke(lapin.couleur);
                gc.strokeOval(x0, y0, resolution, resolution);
                image = imageLapin;
            }
            if (image != null) {
                gc.drawImage(image, lutin.coord.x * resolution, lutin.coord.y * resolution, resolution, resolution);
            }
        }
    }

}
