package com.github.gquintana.laapin.moteur;

import com.github.gquintana.laapin.joueur.Coord;

public abstract class Lutin {

    public Coord coord;

    protected Lutin(Coord coord) {
        this.coord = coord;
    }

    public abstract com.github.gquintana.laapin.joueur.Lutin photographier();

    @Override
    public String toString() {
        return getClass().getSimpleName() + " " + coord;
    }
}
