package com.github.gquintana.laapin.joueur;

import java.util.Objects;

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

    public boolean estA(Direction direction, Coord autre) {
        return direction.estA(this, autre);
    }
}
