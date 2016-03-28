package com.github.gquintana.laapin.moteur;

import com.github.gquintana.laapin.joueur.Action;
import com.github.gquintana.laapin.joueur.Coord;

public class AvancerActionImpl implements ActionImpl {
    @Override
    public ResultatAction executer(Lapin lapin, Grille grille, Action action) {
        Coord coordCible = action.direction.calculer(lapin.coord, 1);
        if (!grille.contient(coordCible)) {
            return new ResultatAction(action, "Pas par là");
        }
        Lapin autreLapin = grille.getLapin(coordCible);
        if (autreLapin != null) {
            return new ResultatAction(action, "Occupé");
        }
        Carotte carotte = grille.getCarotte(coordCible);
        lapin.coord = coordCible;
        if (carotte != null) {
            ResultatAction resultatAction = new ResultatAction(action, "Miam");
            lapin.manger(carotte);
            grille.supprimerCarotte(carotte);
            return resultatAction;
        }
        return new ResultatAction(action, "En avant");
    }
}
