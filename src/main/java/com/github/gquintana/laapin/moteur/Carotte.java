package com.github.gquintana.laapin.moteur;

import com.github.gquintana.laapin.joueur.Coord;

public class Carotte extends Lutin{

    public Carotte(Coord coord) {
        super(coord);
    }

    public com.github.gquintana.laapin.joueur.Carotte photographier() {
        return new com.github.gquintana.laapin.joueur.Carotte(coord);
    }

}
