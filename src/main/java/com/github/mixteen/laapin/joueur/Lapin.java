package com.github.mixteen.laapin.joueur;


import javafx.scene.paint.Color;

public class Lapin extends Lutin {
    public final String nom;
    public final Color couleur;
    public final Action dernierAction;
    public final int energie;
    public final Joueur joueur;

    public Lapin(Coord coord, String nom, Color couleur, Action dernierAction, int energie, Joueur joueur) {
        super(coord);
        this.nom = nom;
        this.couleur = couleur;
        this.dernierAction = dernierAction;
        this.energie = energie;
        this.joueur = joueur;
    }

    @Override
    public String toString() {
        return "Lapin " + nom + " " + coord;
    }

    public boolean estFatigue() {
        return energie < 0;
    }
}
