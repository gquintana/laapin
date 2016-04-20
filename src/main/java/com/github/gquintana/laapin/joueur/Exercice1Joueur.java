package com.github.gquintana.laapin.joueur;

import static com.github.gquintana.laapin.joueur.Action.avancer;
import static com.github.gquintana.laapin.joueur.Action.seReposer;

public class Exercice1Joueur implements Joueur {
    @Override
    public Action reflechir(Lapin monLapin, Grille grille) {
        Carotte carotte = grille.carotteProche(monLapin);
        if (carotte == null) {
            return seReposer();
        } else if (carotte.estADroite().de(monLapin)) {
            return avancer().aDroite();
        }
        return null;
    }
}
