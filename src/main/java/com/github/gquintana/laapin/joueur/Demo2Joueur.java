package com.github.gquintana.laapin.joueur;

public class Demo2Joueur implements Joueur {
    @Override
    public Action reflechir(Lapin monLapin, Grille grille) {
        Carotte carotte = grille.carotteProche(monLapin);
        if (carotte != null) {
            Distancier distancierCarotte = grille.distancierVers(carotte);
            Direction directionCarotte = distancierCarotte.directionDepuis(monLapin);
            return TypeAction.AVANCER.vers(directionCarotte);
        }
        return null;
    }
}
