package com.github.gquintana.laapin.moteur;

import com.github.gquintana.laapin.joueur.Coord;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Grille {
    public final Coord taille;
    public final List<Lutin> lutins;

    public Grille(Coord taille, List<Lutin> lutins) {
        this.taille = taille;
        this.lutins = new ArrayList<>(lutins);
    }

    public boolean contient(Coord coord) {
        return coord.x >= 0 && coord.y >= 0 && coord.x < taille.x && coord.y < taille.y;
    }
     <L extends Lutin> Stream<L> lutinStream(Class<L> lutinClass) {
        return lutins.stream().filter(lutinClass::isInstance).map(lutinClass::cast);
    }

    public Lapin getLapin(Coord coord) {
        return lutinStream(Lapin.class).filter(l -> l.coord.equals(coord)).findFirst().orElse(null);
    }

    public List<Lapin> lapins() {
        return lutinStream(Lapin.class).collect(Collectors.toList());
    }

    public Carotte getCarotte(Coord coord) {
        return lutinStream(Carotte.class).filter(l -> l.coord.equals(coord)).findFirst().orElse(null);
    }

    public com.github.gquintana.laapin.joueur.Grille photographier() {
        List<com.github.gquintana.laapin.joueur.Lutin> lutinsPhoto = lutins.stream()
                .map(Lutin::photographier)
                .collect(Collectors.toList());
        return new com.github.gquintana.laapin.joueur.Grille(taille, lutinsPhoto);
    }

    public void supprimerCarotte(Carotte carotte) {
        lutins.remove(carotte);
    }

    public Lutin getLutin(Coord coord) {
        return lutins.stream().filter(l -> l.coord.equals(coord)).findFirst().orElse(null);
    }
}
