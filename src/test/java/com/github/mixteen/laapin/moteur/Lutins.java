package com.github.mixteen.laapin.moteur;

import com.github.mixteen.laapin.joueur.Coord;
import com.github.mixteen.laapin.joueur.Joueur;
import javafx.scene.paint.Color;

public class Lutins {
    public static Lapin lapin(String nom, Joueur joueur, int x, int y) {
        return new Lapin(new Coord(x, y), nom, Color.BLUE, null, 0, joueur);
    }
    public static Lapin lapin(String nom, int x, int y) {
        return lapin(nom, (monLapin, grille) -> null, x, y);
    }
    public static Carotte carotte(int x, int y) {
        return new Carotte(new Coord(x, y));
    }
    public static Rocher rocher(int x, int y) {
        return new Rocher(new Coord(x, y));
    }

}
