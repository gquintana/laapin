package com.github.gquintana.laapin.joueur;

import static com.github.gquintana.laapin.joueur.Action.avancer;
import static com.github.gquintana.laapin.joueur.Action.seReposer;

public class Demo1Joueur implements Joueur {
    @Override
    public Action reflechir(Lapin monLapin, Grille grille) {
        Carotte carotte = grille.carotteProche(monLapin);
        if (carotte == null) {
            return seReposer();
        } else if (carotte.estADroite().de(monLapin)) {
            return avancer().aDroite();

        } else if (carotte.estAGauche().de(monLapin)) {
            return avancer().aGauche();

        } else if (carotte.estEnHaut().de(monLapin)) {
            return avancer().enHaut();

        } else if (carotte.estEnBas().de(monLapin)) {
            return avancer().enBas();
        }
        return null;
    }
}
