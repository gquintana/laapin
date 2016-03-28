package com.github.gquintana.laapin.moteur;

import com.github.gquintana.laapin.joueur.Coord;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Grille {
    public final Coord taille;
    public final List<Lapin> lapins;
    public final List<Carotte> carottes;

    public Grille(Coord taille, List<Lapin> lapins, List<Carotte> carottes) {
        this.taille = taille;
        this.lapins = new ArrayList<>(lapins);
        this.carottes = new ArrayList<>(carottes);
    }

    public boolean contient(Coord coord) {
        return coord.x >= 0 && coord.y >= 0 && coord.x < taille.x && coord.y < taille.y;
    }

    public Lapin getLapin(Coord coord) {
        return lapins.stream().filter(l -> l.coord.equals(coord)).findFirst().orElse(null);
    }

    public Carotte getCarotte(Coord coord) {
        return carottes.stream().filter(l -> l.coord.equals(coord)).findFirst().orElse(null);
    }

    public com.github.gquintana.laapin.joueur.Grille photographier() {
        List<com.github.gquintana.laapin.joueur.Lapin> lapinsPhoto = lapins.stream()
                .map(Lapin::photographier)
                .collect(Collectors.toList());
        List<com.github.gquintana.laapin.joueur.Carotte> carottesPhoto = carottes.stream()
                .map(Carotte::photographier)
                .collect(Collectors.toList());
        return new com.github.gquintana.laapin.joueur.Grille(taille, lapinsPhoto, carottesPhoto);
    }

    public void supprimerCarotte(Carotte carotte) {
        carottes.remove(carotte);
    }

}
