package com.github.gquintana.laapin.moteur;

import com.github.gquintana.laapin.joueur.Coord;

public class Rocher extends Lutin {
    public Rocher(Coord coord) {
        super(coord);
    }

    @Override
    public com.github.gquintana.laapin.joueur.Lutin photographier() {
        return new com.github.gquintana.laapin.joueur.Rocher(coord);
    }
}
