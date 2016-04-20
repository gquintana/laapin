package com.github.mixteen.laapin.moteur;

import com.github.mixteen.laapin.joueur.Coord;

public class Carotte extends Lutin{

    public Carotte(Coord coord) {
        super(coord);
    }

    public com.github.mixteen.laapin.joueur.Carotte photographier() {
        return new com.github.mixteen.laapin.joueur.Carotte(coord);
    }

}
