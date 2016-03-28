package com.github.gquintana.laapin.moteur;

import com.github.gquintana.laapin.joueur.Action;

public interface ActionImpl {
    ResultatAction executer(Lapin lapin, Grille grille, Action action);
}
