package com.github.gquintana.laapin.joueur;


import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Grille {
    public final Coord taille;
    public final List<Lutin> lutins;

    public Grille(Coord taille, List<Lutin> lutins) {
        this.taille = taille;
        this.lutins = lutins;
    }

    /**
     * Teste si une cellule est dans le champ
     */
    public boolean contient(Coord coord) {
        return coord.x >= 0 && coord.y >= 0 && coord.x < taille.x && coord.y < taille.y;
    }

    private <L extends Lutin> Stream<L> lutinStream(Class<L> lutinClass) {
        return lutins.stream().filter(lutinClass::isInstance).map(lutinClass::cast);
    }

    private <L extends Lutin> boolean contient(Coord coord, Class<L> lutinClass) {
        return lutins.stream().filter(l -> l.coord.equals(coord) && lutinClass.isInstance(l)).findFirst().isPresent();
    }

    /**
     * Teste si une cellule contient un lapin
     */
    public boolean contientLapin(Coord coord) {
        return contient(coord, Lapin.class);
    }

    /**
     * Teste si une cellule contient une carotte
     */
    public boolean contientCarotte(Coord coord) {
        return contient(coord, Carotte.class);
    }

    /**
     * Teste si une cellule contient un rocher
     */
    public boolean contientRocher(Coord coord) {
        return contient(coord, Rocher.class);
    }

    /**
     * Retourne le contenu d'une cellule: carotte ou lapin ou <code>null</code>
     */
    public Lutin lutin(final Coord coord) {
        Optional<Lutin> lutin = lutins.stream().filter(l -> l.coord.equals(coord)).findAny();
        return lutin.orElse(null);
    }

    /**
     * Retourne la carotte la plus proche ou <code>null</code> si plus de carotte
     */
    public Carotte carotteProche(final Lapin monLapin) {
        return lutinStream(Carotte.class)
                .sorted(Comparator.comparing(l -> monLapin.coord.distance(l.coord)))
                .findFirst().orElse(null);
    }

    /**
     * Retourne la liste des carottes triées par proximité
     */
    public List<Carotte> carottesProches(final Lapin monLapin) {
        return lutinStream(Carotte.class)
                .sorted(Comparator.comparing(l -> monLapin.coord.distance(l.coord)))
                .collect(Collectors.toList());
    }

    private Stream<Lapin> lapinsStream(Lapin monLapin) {
        return lutinStream(Lapin.class)
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

    /**
     * Teste si la cellule contient un obstacle: rocher, lapin ou bordure
     */
    public boolean contientObstacle(Coord coord) {
        if (!contient(coord)) {
            return true;
        }
        Lutin lutin = lutin(coord);
        return lutin instanceof Rocher || lutin instanceof Lapin;
    }

    /**
     * Teste si on peut aller dans une cellule
     */
    public boolean estAccessible(Coord coord) {
        return !contientObstacle(coord);
    }

    /**
     * Calcule le distancier pour aller vers une cellule
     */
    public Distancier distancierVers(Coord coord) {
        return new Distancier(this, coord);
    }

    /**
     * Calcule le distancier pour aller vers un lutin
     */
    public Distancier distancierVers(Lutin lutin) {
        return distancierVers(lutin.coord);
    }
}
