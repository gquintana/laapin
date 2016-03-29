package com.github.gquintana.laapin;

import com.github.gquintana.laapin.moteur.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class GrillePanel extends JPanel {
    public static final int RESOLUTION = 64;
    private final Grille grille;

    private final BufferedImage imageFond;
    final BufferedImage imageLapin;
    private final BufferedImage imageCarotte;
    private final BufferedImage imageRocher;


    public GrillePanel(Grille grille) throws IOException {
        this.grille = grille;
        int width = grille.taille.x * RESOLUTION;
        int height = grille.taille.y * RESOLUTION;
        setSize(width, height);
        setPreferredSize(new Dimension(width, height));
        imageFond = loadImage("fond");
        imageLapin = loadImage("lapin");
        imageCarotte = loadImage("carotte");
        imageRocher = loadImage("rocher");
    }

    private BufferedImage loadImage(String name) throws IOException {
        return ImageIO.read(getClass().getResourceAsStream(name + "-" + RESOLUTION + ".png"));
    }

    private void doDrawing(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        for (int x = 0; x < grille.taille.x; x++) {
            for (int y = 0; y < grille.taille.y; y++) {
                g2d.drawImage(imageFond, x * RESOLUTION, y * RESOLUTION, RESOLUTION, RESOLUTION, null);
            }
        }
        for (Lutin lutin : grille.lutins) {
            BufferedImage image = null;
            if (lutin instanceof Carotte) {
                image = imageCarotte;
            } else if (lutin instanceof Rocher) {
                image = imageRocher;
            } else if (lutin instanceof Lapin) {
                Lapin lapin = (Lapin) lutin;
                int x0 = lapin.coord.x * RESOLUTION;
                int y0 = lapin.coord.y * RESOLUTION;
                g2d.setPaint(lapin.couleur);
                g2d.drawOval(x0, y0, RESOLUTION, RESOLUTION);
                image = imageLapin;
            }
            if (image != null) {
                g2d.drawImage(image, lutin.coord.x * RESOLUTION, lutin.coord.y * RESOLUTION, RESOLUTION, RESOLUTION, null);
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }
}
