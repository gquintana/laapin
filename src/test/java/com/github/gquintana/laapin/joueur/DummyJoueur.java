package com.github.gquintana.laapin.joueur;

public class DummyJoueur implements Joueur {
    @Override
    public Action reflechir(Lapin monLapin, Grille grille) {
        return new Action(TypeAction.SE_REPOSER, null);
    }
}
