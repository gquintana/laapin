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
    private Grille grille;
    private final Canvas canvas;
    private final Image imageFond;
    public final Image imageLapin;
    private final Image imageCarotte;
    private final Image imageRocher;
    /**
     * Affiche le fond de la grille
     */
    private final boolean fond;
    /**
     * Affiche les coordonnées des cases
     */
    private final boolean coordonnees;
    /**
     * Affiche le distancier
     */
    private final boolean distancier;


    public GrillePanel(Configuration configuration) throws IOException {
        this.resolution = configuration.getInt("grille.resolution", 64);
        this.coordonnees = configuration.getBoolean("grille.coordonnees", false);
        this.distancier = configuration.getBoolean("grille.distancier", false);
        int width = configuration.getInt("grille.taille.x", 10) * resolution;
        int height = configuration.getInt("grille.taille.y", 10) * resolution;
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
        if (grille == null) {
            return;
        }
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.WHITE);
        gc.setStroke(Color.BLACK);
        Distancier leDistancier = null;
        Double distanceMax = null;
        if (distancier) {
            Carotte carotte = (Carotte) grille.lutins.stream().filter(l -> l instanceof Carotte).findAny().orElse(null);
            if (carotte != null) {
                leDistancier = grille.photographier().distancierVers(carotte.coord);
                distanceMax = (double) leDistancier.distanceMax();
            }
        }
        for (int x = 0; x < grille.taille.x; x++) {
            for (int y = 0; y < grille.taille.y; y++) {
                if (fond) {
                    gc.drawImage(imageFond, coordX(x), coordY(y), resolution, resolution);
                } else {
                    Color couleurFond = Color.WHITE;
                    if (leDistancier != null) {
                        double d = Math.min(1.0D, Math.max(0.0D, 1.0D - ((double) leDistancier.distance(new Coord(x, y))) / distanceMax));
                        System.out.println(((double) leDistancier.distance(new Coord(x, y))) / distanceMax + " " + d);
                        couleurFond = new Color(d, d, d, 1.0D);
                    }
                    gc.setFill(couleurFond);
                    gc.fillRect(coordX(x), coordY(y), resolution, resolution);
                    gc.strokeRect(coordX(x), coordY(y), resolution, resolution);
                }
                if (coordonnees) {
                    gc.setFill(Color.DARKGRAY);
                    gc.fillText(x + "," + y, coordX(x) + 16, coordY(y) + 8, resolution / 2);
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
                double x0 = coordX(lapin.coord.x);
                double y0 = coordY(lapin.coord.y);
                gc.setStroke(lapin.couleur);
                gc.strokeOval(x0, y0, resolution, resolution);
                paintLutin(gc, lutin, imageLapin);
                gc.setFill(lapin.couleur);
                gc.fillText(lapin.initiale(), x0 + resolution / 2, y0 + 3 * resolution / 4, resolution);
            }
        }
    }

    private double coordX(int x) {
        return x * resolution;
    }

    private double coordY(int y) {
        return (grille.taille.y - y - 1) * resolution;
    }

    private void paintLutin(GraphicsContext gc, Lutin lutin, Image image) {
        if (image != null) {
            gc.drawImage(image, coordX(lutin.coord.x), coordY(lutin.coord.y), resolution, resolution);
        }
    }

    public void setGrille(Grille grille) {
        this.grille = grille;
    }
}
