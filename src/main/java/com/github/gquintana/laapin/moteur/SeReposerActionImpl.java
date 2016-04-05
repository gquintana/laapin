package com.github.gquintana.laapin.moteur;

import com.github.gquintana.laapin.joueur.Action;

public class SeReposerActionImpl implements ActionImpl {
    @Override
    public ResultatAction executer(Lapin lapin, Grille grille, Action action) {
        lapin.seReposer();
        return new ResultatAction(lapin, action, "Zzzz");
    }
}
