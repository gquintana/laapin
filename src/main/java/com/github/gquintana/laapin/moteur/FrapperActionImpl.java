package com.github.gquintana.laapin.moteur;

import com.github.gquintana.laapin.joueur.Action;
import com.github.gquintana.laapin.joueur.Coord;

public class FrapperActionImpl implements ActionImpl {
    @Override
    public ResultatAction executer(Lapin lapin, Grille grille, Action action) {
        Coord coordCible = action.direction.de(lapin.coord);
        Lapin lapinCible = grille.getLapin(coordCible);
        if (lapinCible == null) {
            return new ResultatAction(lapin, action, "Raté");
        }
        lapinCible.recevoirCoup();
        return new ResultatAction(lapin, action, "Touché "+lapin.nom);
    }
}
