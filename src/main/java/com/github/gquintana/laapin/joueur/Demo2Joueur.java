package com.github.gquintana.laapin.joueur;

import static com.github.gquintana.laapin.joueur.Action.avancer;

public class Demo2Joueur implements Joueur {
    @Override
    public Action reflechir(Lapin monLapin, Grille grille) {
        Carotte carotte = grille.carotteProche(monLapin);
        if (carotte != null) {
            Distancier distancierCarotte = grille.distancierVers(carotte);
            int distanceCarotte = distancierCarotte.distance(monLapin);
            Direction directionCarotte = distancierCarotte.directionDepuis(monLapin);
            return avancer().vers(directionCarotte);
        }
        return null;
    }
}
