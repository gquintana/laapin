package com.github.mixteen.laapin.moteur;

import com.github.mixteen.laapin.joueur.Coord;

import java.util.Objects;

public abstract class Lutin {

    protected Coord coord;

    protected Lutin(Coord coord) {
        this.coord = coord;
    }

    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }

    public abstract com.github.mixteen.laapin.joueur.Lutin photographier();

    @Override
    public String toString() {
        return getClass().getSimpleName() + " " + coord;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lutin lutin = (Lutin) o;
        return Objects.equals(coord, lutin.coord);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coord);
    }
}
