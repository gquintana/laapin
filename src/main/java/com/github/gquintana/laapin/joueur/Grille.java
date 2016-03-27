package com.github.gquintana.laapin.joueur;


import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Grille {
    public final Coord taille;
    public final List<Lapin> lapins;
    public final List<Carotte> carottes;

    public Grille(Coord taille, List<Lapin> lapins, List<Carotte> carottes) {
        this.taille = taille;
        this.lapins = lapins;
        this.carottes = carottes;
    }

    /**
     * Teste si une cellule est dans le champ
     */
    public boolean contient(Coord coord) {
        return coord.x >= 0 && coord.y >= 0 && coord.x < taille.x && coord.y < taille.y;
    }

    /**
     * Teste si une cellule contient un lapin
     */
    public boolean contientLapin(Coord coord) {
        return lapins.stream().filter(l -> l.coord.equals(coord)).findFirst().isPresent();
    }

    /**
     * Teste si une cellule contient une carotte
     */
    public boolean contientCarotte(Coord coord) {
        return carottes.stream().filter(l -> l.coord.equals(coord)).findFirst().isPresent();
    }

    /** Retourne le contenu d'une cellule: carotte ou lapin ou <code>null</code> */
    public Lutin lutin(final Coord coord) {
        Optional<Carotte> carotte = carottes.stream().filter(l -> l.coord.equals(coord)).findAny();
        if (carotte.isPresent()) {
            return carotte.get();
        }
        Optional<Lapin> lapin = lapins.stream().filter(l -> l.coord.equals(coord)).findAny();
        if (lapin.isPresent()) {
            return lapin.get();
        }
        return null;
    }

    /**
     * Retourne la carotte la plus proche ou <code>null</code> si plus de carotte
     */
    public Carotte carotteProche(final Lapin monLapin) {
        return carottes.stream()
                .sorted(Comparator.comparing(l -> monLapin.coord.distance(l.coord)))
                .findFirst().orElse(null);
    }

    /**
     * Retourne la liste des carottes triées par proximité
     */
    public List<Carotte> carottesProches(final Lapin monLapin) {
        return carottes.stream()
                .sorted(Comparator.comparing(l -> monLapin.coord.distance(l.coord)))
                .collect(Collectors.toList());
    }

    private Stream<Lapin> lapinsStream(Lapin monLapin) {
        return lapins.stream()
                .filter(l -> l.coord != monLapin.coord);
    }

    /**
     * Retourne le lapin le plus proche ou <code>null</code> si aucun lapin
     */
    public Lapin lapinProche(final Lapin monLapin) {
        return lapinsStream(monLapin)
                .sorted(Comparator.comparing(l -> monLapin.coord.distance(l.coord)))
                .findFirst().orElse(null);
    }

    /**
     * Retourne la liste des lapins triés par proximité
     */
    public List<Lapin> lapinsProches(final Lapin monLapin) {
        return lapinsStream(monLapin)
                .sorted(Comparator.comparing(l -> monLapin.coord.distance(l.coord)))
                .collect(Collectors.toList());
    }

}
