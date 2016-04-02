package com.github.gquintana.laapin.moteur;

import com.github.gquintana.laapin.joueur.Action;
import com.github.gquintana.laapin.joueur.Coord;

public class AvancerActionImpl implements ActionImpl {
    @Override
    public ResultatAction executer(Lapin lapin, Grille grille, Action action) {
        Coord coordCible = action.direction.de(lapin.coord);
        if (!grille.contient(coordCible)) {
            return new ResultatAction(lapin, action, "Pas par là");
        }
        Lutin lutin = grille.getLutin(coordCible);
        if (lutin instanceof Lapin) {
            return new ResultatAction(lapin, action, "Occupé");
        } else if (lutin instanceof Rocher) {
            return new ResultatAction(lapin, action, "Pas d'escalade");
        } else {
            lapin.coord = coordCible;
            if (lutin instanceof Carotte) {
                Carotte carotte = (Carotte) lutin;
                ResultatAction resultatAction = new ResultatAction(lapin, action, "Miam");
                lapin.manger(carotte);
                grille.supprimerCarotte(carotte);
                return resultatAction;
            }
            return new ResultatAction(lapin, action, "En avant");
        }
    }
}
