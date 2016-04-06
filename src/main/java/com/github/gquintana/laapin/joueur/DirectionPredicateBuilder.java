package com.github.gquintana.laapin.joueur;

public class DirectionPredicateBuilder {
    private final Coord source;
    private final Direction direction;

    public DirectionPredicateBuilder(Lutin source, Direction direction) {
        this(source.coord, direction);
    }
    public DirectionPredicateBuilder(Coord source, Direction direction) {
        this.source = source;
        this.direction = direction;
    }
    public boolean de(Lutin cible) {
        return de(cible.coord);
    }

    public boolean de(Coord cible) {
        boolean b = direction.estA(source, cible);
        System.out.println(source + " est à " + direction + " de " + cible + ": " + b);
        return b;
    }
}
