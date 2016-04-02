package com.github.gquintana.laapin.moteur;

import com.github.gquintana.laapin.joueur.Action;
import com.github.gquintana.laapin.joueur.Coord;

public class SauterActionImpl implements ActionImpl {
    @Override
    public ResultatAction executer(Lapin lapin, Grille grille, Action action) {
        Coord coordCible = action.direction.de(lapin.coord);
        Coord coordCible2 = action.direction.de(lapin.coord, 2);
        Lapin lapinCible = grille.getLapin(coordCible);
        if (lapinCible == null) {
            return new ResultatAction(lapin, action, "???");
        }
        if (!grille.contient(coordCible2)) {
            return new ResultatAction(lapin, action, "Pas par là");
        }
        Carotte carotte = grille.getCarotte(coordCible2);
        lapin.coord = coordCible2;
        if (carotte == null) {
            return new ResultatAction(lapin, action, "Et hop");
        } else {
            lapin.manger(carotte);
            return new ResultatAction(lapin, action, "Miam");
        }
    }
}
