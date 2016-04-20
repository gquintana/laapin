package com.github.mixteen.laapin.moteur;

import com.github.mixteen.laapin.joueur.Action;
import com.github.mixteen.laapin.joueur.Coord;
import com.github.mixteen.laapin.joueur.Joueur;
import javafx.scene.paint.Color;


public class Lapin extends Lutin {
    public final String nom;
    public final Color couleur;
    private Action derniereAction;
    private int energie;
    public final Joueur joueur;
    private int score;

    public Lapin(Coord coord, String nom, Color couleur, Action derniereAction, int energie, Joueur joueur) {
        super(coord);
        this.nom = nom;
        this.couleur = couleur;
        this.derniereAction = derniereAction;
        this.energie = energie;
        this.joueur = joueur;
    }

    public com.github.mixteen.laapin.joueur.Lapin photographier() {
        return new com.github.mixteen.laapin.joueur.Lapin(coord, nom, couleur, derniereAction, energie, joueur);
    }

    public boolean estFatigue() {
        return energie < 0;
    }

    public void manger(Carotte carotte) {
        score++;
    }

    public void recevoirCoup() {
        energie = -2;
    }

    public void seReposer() {
        energie = Math.min(energie + 1, 0);
    }

    @Override
    public String toString() {
        return "Lapin " + nom + " " + coord;
    }

    public int score() {
        return score;
    }

    public String initiale() {
        if (nom == null) {
            return "?";
        }
        String initiale = nom.trim();
        if (initiale.isEmpty()) {
            return "?";
        }
        return initiale.substring(0, 1);
    }

    public void agir(Action action) {
        this.derniereAction = action;
    }

    public int energie() {
        return energie;
    }
}
