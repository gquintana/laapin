package com.github.mixteen.laapin.joueur;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Coordonnées d'une cellule
 */
public class Coord {
    public final int x;
    public final int y;

    public Coord(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coord coord = (Coord) o;
        return x == coord.x && y == coord.y;
    }

    /**
     * Distance en nombre de pas
     */
    public int distance(Coord coord) {
        return Math.abs(coord.x - x) + Math.abs(coord.y - y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ')';
    }

    /**
     * Teste si la cellulle est à gauche/droite/haut/bas d'une autre
     */
    public boolean estA(Direction direction, Coord autre) {
        return direction.estA(this, autre);
    }

    /**
     * Teste si le cellule est en haut/bas/gauche/droite d'un autre cellule
     */
    public DirectionPredicateBuilder estA(Direction direction) {
        return new DirectionPredicateBuilder(this, direction);
    }

    /**
     * Teste si le cellule est à droite d'un autre cellule
     */
    public DirectionPredicateBuilder estADroite() {
        return estA(Direction.DROITE);
    }

    /**
     * Teste si le cellule est à gauche d'un autre cellule
     */
    public DirectionPredicateBuilder estAGauche() {
        return estA(Direction.GAUCHE);
    }

    /**
     * Teste si le cellule est en haut d'un autre cellule
     */
    public DirectionPredicateBuilder estEnHaut() {
        return estA(Direction.HAUT);
    }

    /**
     * Teste si le cellule est en bas d'un autre cellule
     */
    public DirectionPredicateBuilder estEnBas() {
        return estA(Direction.BAS);
    }

    /**
     * Calcule la ou les direction pour aller vers la cellule cible
     */
    List<Direction> directionsVers(final Coord cible) {
        return Arrays.stream(Direction.values()).filter(d -> d.estA(cible, this)).collect(Collectors.toList());
    }

    /**
     * Calcule les coordonnées dans une direction
     */
    public Coord coordVers(Direction direction) {
        return direction.de(this);
    }
}
