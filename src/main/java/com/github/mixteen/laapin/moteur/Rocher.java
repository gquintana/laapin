package com.github.mixteen.laapin.moteur;

import com.github.mixteen.laapin.joueur.Coord;

public class Rocher extends Lutin {
    public Rocher(Coord coord) {
        super(coord);
    }

    @Override
    public com.github.mixteen.laapin.joueur.Lutin photographier() {
        return new com.github.mixteen.laapin.joueur.Rocher(coord);
    }
}
