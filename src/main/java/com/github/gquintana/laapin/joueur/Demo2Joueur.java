package com.github.gquintana.laapin.joueur;

import static com.github.gquintana.laapin.joueur.Action.*;

public class Demo2Joueur implements Joueur {
    @Override
    public Action reflechir(Lapin monLapin, Grille grille) {
        Carotte carotte = grille.carotteProche(monLapin);
        Direction directionCarotte = monLapin.directionVers(carotte);
        if (carotte == null) {
            return seReposer();
        }
        Lapin autreLapin = grille.lapinProche(monLapin);
        if (autreLapin.distance(monLapin) == 1) {
            Direction directionLapin = monLapin.directionVers(autreLapin);
            if (directionLapin == directionCarotte && !grille.contientObstacle(monLapin.coordVers(directionLapin, 2))) {
                return sauter().vers(directionLapin);
            } else if (!autreLapin.estFatigue()){
                return frapper().vers(directionLapin);
            }
        }
        if (carotte.estADroite().de(monLapin)) {
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
