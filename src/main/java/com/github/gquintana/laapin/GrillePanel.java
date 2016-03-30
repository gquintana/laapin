package com.github.gquintana.laapin;

import com.github.gquintana.laapin.moteur.*;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.IOException;

class GrillePanel extends Group {
    private static final int RESOLUTION = 64;
    private final Grille grille;
    private final Canvas canvas;
    private final Image imageFond;
    final Image imageLapin;
    private final Image imageCarotte;
    private final Image imageRocher;


    public GrillePanel(Grille grille) throws IOException {
        this.grille = grille;
        int width = grille.taille.x * RESOLUTION;
        int height = grille.taille.y * RESOLUTION;
        canvas = new Canvas(width, height);
        getChildren().add(canvas);
        imageFond = loadImage("fond");
        imageLapin = loadImage("lapin");
        imageCarotte = loadImage("carotte");
        imageRocher = loadImage("rocher");
    }

    private Image loadImage(String name) {
        return new Image(getClass().getResourceAsStream(name + "-" + RESOLUTION + ".png"));
    }

    public void repaint() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        for (int x = 0; x < grille.taille.x; x++) {
            for (int y = 0; y < grille.taille.y; y++) {
                gc.drawImage(imageFond, x * RESOLUTION, y * RESOLUTION, RESOLUTION, RESOLUTION);
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
                int x0 = lapin.coord.x * RESOLUTION;
                int y0 = lapin.coord.y * RESOLUTION;
                gc.setStroke(lapin.couleur);
                gc.strokeOval(x0, y0, RESOLUTION, RESOLUTION);
                image = imageLapin;
            }
            if (image != null) {
                gc.drawImage(image, lutin.coord.x * RESOLUTION, lutin.coord.y * RESOLUTION, RESOLUTION, RESOLUTION);
            }
        }
    }

}
