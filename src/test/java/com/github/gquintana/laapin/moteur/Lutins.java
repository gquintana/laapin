package com.github.gquintana.laapin.moteur;

import com.github.gquintana.laapin.joueur.Action;
import com.github.gquintana.laapin.joueur.Coord;
import com.github.gquintana.laapin.joueur.Joueur;

import java.awt.*;
import java.util.List;

public class Lutins {
    public static Lapin lapin(String nom, Joueur joueur, int x, int y) {
        return new Lapin(new Coord(x, y), "Test", Color.BLUE, null, 0, joueur);
    }
    public static Lapin lapin(String nom, int x, int y) {
        return lapin(nom, new Joueur() {
            @Override
            public Action reflechir(com.github.gquintana.laapin.joueur.Lapin monLapin, com.github.gquintana.laapin.joueur.Grille grille) {
                return null;
            }
        }, x, y);
    }
    public static Carotte carotte(int x, int y) {
        return new Carotte(new Coord(x, y));
    }

}
