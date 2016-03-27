package com.github.gquintana.laapin.moteur;

import com.github.gquintana.laapin.joueur.Action;
import com.github.gquintana.laapin.joueur.Coord;

public class FrapperCommande implements Commande {
    @Override
    public void executer(Lapin lapin, Grille grille, Action action) {
        Coord coordCible = action.direction.calculer(lapin.coord, 1);
        Lapin lapinCible = grille.getLapin(coordCible);
        if (lapinCible == null) {
            grille.setDernierMessage("Raté");
            return;
        }
        grille.setDernierMessage("Touché "+lapin.nom);
        lapinCible.recevoirCoup();
    }
}
