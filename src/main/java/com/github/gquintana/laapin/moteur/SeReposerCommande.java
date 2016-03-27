package com.github.gquintana.laapin.moteur;

import com.github.gquintana.laapin.joueur.Action;

public class SeReposerCommande implements Commande {
    @Override
    public void executer(Lapin lapin, Grille grille, Action action) {
        grille.setDernierMessage("Zzzz");
        if (lapin.fatigue > 0) {
            lapin.fatigue--;
        }
    }
}
