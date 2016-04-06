package com.github.gquintana.laapin.affichage;

import com.github.gquintana.laapin.Configuration;
import com.github.gquintana.laapin.joueur.Coord;
import com.github.gquintana.laapin.joueur.Distancier;
import com.github.gquintana.laapin.moteur.*;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

import java.io.IOException;

public class GrillePanel extends Group {
    private final int resolution;
    private final Grille grille;
    private final Canvas canvas;
    private final Image imageFond;
    public final Image imageLapin;
    private final Image imageCarotte;
    private final Image imageRocher;
    /**
     * Affiche le fond de la grille
     */
    private final boolean fond;


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
        fond = configuration.getBoolean("grille.fond", true);
        repaint();
    }

    private Image loadImage(String name) {
        return new Image(getClass().getResourceAsStream(name + "-" + resolution + ".png"));
    }

    public void repaint() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.WHITE);
        gc.setStroke(Color.BLACK);
        Carotte carotte = (Carotte) grille.lutins.stream().filter(l -> l instanceof Carotte).findAny().orElse(null);
        Distancier distancier = grille.photographier().distancierVers(carotte.coord);
        double distanceMax = distancier.distanceMax();
        for (int x = 0; x < grille.taille.x; x++) {
            for (int y = 0; y < grille.taille.y; y++) {
                if (fond) {
                    gc.drawImage(imageFond, x * resolution, y * resolution, resolution, resolution);
                } else {
                    double d = Math.min(1.0D, Math.max(0.0D, 1.0D - ((double) distancier.distance(new Coord(x, y)))/ distanceMax));
                    System.out.println(((double) distancier.distance(new Coord(x, y)))/ distanceMax+" "+d);
                    Color color = new Color(d,d,d,1.0D);
                    gc.setFill(color);
                    gc.fillRect(x * resolution, y * resolution, resolution, resolution);
                    gc.strokeRect(x * resolution, y * resolution, resolution, resolution);
                }
            }
        }
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.CENTER);
        for (Lutin lutin : grille.lutins) {
            if (lutin instanceof Carotte) {
                paintLutin(gc, lutin, imageCarotte);
            } else if (lutin instanceof Rocher) {
                paintLutin(gc, lutin, imageRocher);
            } else if (lutin instanceof Lapin) {
                Lapin lapin = (Lapin) lutin;
                int x0 = lapin.coord.x * resolution;
                int y0 = lapin.coord.y * resolution;
                gc.setStroke(lapin.couleur);
                gc.strokeOval(x0, y0, resolution, resolution);
                paintLutin(gc, lutin, imageLapin);
                gc.setFill(lapin.couleur);
                gc.fillText(lapin.initiale(), x0+resolution/2, y0+3*resolution/4, resolution);
            }
        }
    }

    private void paintLutin(GraphicsContext gc, Lutin lutin, Image image) {
        if (image != null) {
            gc.drawImage(image, lutin.coord.x * resolution, lutin.coord.y * resolution, resolution, resolution);
        }
    }

}
