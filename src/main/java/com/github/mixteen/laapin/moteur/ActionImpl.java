package com.github.mixteen.laapin.moteur;

import com.github.mixteen.laapin.joueur.Action;

interface ActionImpl {
    ResultatAction executer(Lapin lapin, Grille grille, Action action);
}
