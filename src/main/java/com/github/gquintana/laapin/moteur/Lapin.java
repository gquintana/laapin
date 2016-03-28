package com.github.gquintana.laapin.moteur;

import com.github.gquintana.laapin.joueur.Action;
import com.github.gquintana.laapin.joueur.Coord;
import com.github.gquintana.laapin.joueur.Joueur;

import java.awt.*;

public class Lapin extends Lutin {
    public final String nom;
    public final Color couleur;
    public Action derniereAction;
    public int fatigue;
    public final Joueur joueur;
    private int score;

    public Lapin(Coord coord, String nom, Color couleur, Action derniereAction, int fatigue, Joueur joueur) {
        super(coord);
        this.nom = nom;
        this.couleur = couleur;
        this.derniereAction = derniereAction;
        this.fatigue = fatigue;
        this.joueur = joueur;
    }

    public com.github.gquintana.laapin.joueur.Lapin photographier() {
        return new com.github.gquintana.laapin.joueur.Lapin(coord, nom, couleur, derniereAction, fatigue, joueur);
    }

    public void manger(Carotte carotte) {
        score++;
    }

    public void recevoirCoup() {
        fatigue = 2;
    }

    @Override
    public String toString() {
        return "Lapin " + nom + " " + coord;
    }

    public int score() {
        return score;
    }
}
