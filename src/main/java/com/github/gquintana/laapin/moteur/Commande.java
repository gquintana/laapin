package com.github.gquintana.laapin.moteur;

import com.github.gquintana.laapin.joueur.Action;

public interface Commande {
    void executer(Lapin lapin, Grille grille, Action action);
}
