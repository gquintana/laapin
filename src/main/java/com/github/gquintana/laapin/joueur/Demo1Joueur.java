package com.github.gquintana.laapin.joueur;

public class Demo1Joueur implements Joueur {
    @Override
    public Action reflechir(Lapin monLapin, Grille grille) {
        Carotte carotte = grille.carotteProche(monLapin);
        if (carotte != null) {
            if (carotte.estA(Direction.DROITE).de(monLapin)) {
                return TypeAction.AVANCER.vers(Direction.DROITE);

            } else if (carotte.estA(Direction.GAUCHE).de(monLapin)) {
                return TypeAction.AVANCER.vers(Direction.GAUCHE);

            } else if (carotte.estA(Direction.HAUT).de(monLapin)) {
                return TypeAction.AVANCER.vers(Direction.HAUT);

            } else if (carotte.estA(Direction.BAS).de(monLapin)) {
                return TypeAction.AVANCER.vers(Direction.BAS);
            }
        }
        return null;
    }
}
