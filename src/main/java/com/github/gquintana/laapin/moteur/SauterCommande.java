package com.github.gquintana.laapin.moteur;

import com.github.gquintana.laapin.joueur.Action;
import com.github.gquintana.laapin.joueur.Coord;

public class SauterCommande implements Commande {
    @Override
    public void executer(Lapin lapin, Grille grille, Action action) {
        Coord coordCible = action.direction.calculer(lapin.coord, 1);
        Coord coordCible2 = action.direction.calculer(lapin.coord, 2);
        Lapin lapinCible = grille.getLapin(coordCible);
        if (lapinCible == null) {
            grille.setDernierMessage("???");
            return;
        }
        if (!grille.contient(coordCible2)) {
            grille.setDernierMessage("Pas par là");
            return;
        }
        Carotte carotte = grille.getCarotte(coordCible2);
        if (carotte == null) {
            grille.setDernierMessage("Et hop");
        } else {
            grille.setDernierMessage("Miam");
            lapin.manger(carotte);
        }
        lapin.coord = coordCible2;
    }
}
