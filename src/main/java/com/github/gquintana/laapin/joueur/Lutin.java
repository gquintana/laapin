package com.github.gquintana.laapin.joueur;

import java.util.List;

public class Lutin {

    public final Coord coord;

    protected Lutin(Coord coord) {
        this.coord = coord;
    }

    public static class DirectionPredicateBuilder {
        private final Lutin source;
        private final Direction direction;

        public DirectionPredicateBuilder(Lutin source, Direction direction) {
            this.source = source;
            this.direction = direction;
        }

        public boolean de(Lutin cible) {
            boolean b = direction.estA(source.coord, cible.coord);
            System.out.println(source + " est à " + direction + " de "+ cible + ": " + b);
            return b;
        }
    }

    public DirectionPredicateBuilder estA(Direction direction) {
        return new DirectionPredicateBuilder(this, direction);
    }

    public List<Direction> directionsVers(Lutin lutin) {
        return coord.directionsVers(lutin.coord);

    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " " + coord;
    }
}
