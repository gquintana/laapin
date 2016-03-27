package com.github.gquintana.laapin.moteur;

import com.github.gquintana.laapin.joueur.Action;
import com.github.gquintana.laapin.joueur.Coord;

public class AvancerCommande implements Commande {
    @Override
    public void executer(Lapin lapin, Grille grille, Action action) {
        Coord coordCible = action.direction.calculer(lapin.coord, 1);
        if (!grille.contient(coordCible)) {
            grille.setDernierMessage("Pas par là");
            return;
        }
        Lapin autreLapin = grille.getLapin(coordCible);
        if (autreLapin != null) {
            grille.setDernierMessage("Occupé");
            return;
        }
        Carotte carotte = grille.getCarotte(coordCible);
        lapin.coord = coordCible;
        if (carotte != null) {
            grille.setDernierMessage("Miam");
            lapin.manger(carotte);
            grille.supprimerCarotte(carotte);
            return;
        }
        grille.setDernierMessage("En avant");
    }
}
