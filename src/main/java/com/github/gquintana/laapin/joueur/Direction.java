package com.github.gquintana.laapin.joueur;

public enum Direction {
    HAUT() {
        @Override
        public Coord de(Coord coord, int distance) {
            return new Coord(coord.x, coord.y + distance);
        }

        @Override
        public boolean estA(Coord coord, Coord autre) {
            return coord.y > autre.y;
        }
    }, BAS() {
        @Override
        public Coord de(Coord coord, int distance) {
            return new Coord(coord.x, coord.y - distance);
        }

        @Override
        public boolean estA(Coord coord, Coord autre) {
            return coord.y < autre.y;
        }
    }, GAUCHE {
        @Override
        public Coord de(Coord coord, int distance) {
            return new Coord(coord.x - distance, coord.y);
        }

        @Override
        public boolean estA(Coord coord, Coord autre) {
            return coord.x < autre.x;
        }
    }, DROITE {
        @Override
        public Coord de(Coord coord, int distance) {
            return new Coord(coord.x + distance, coord.y);
        }

        @Override
        public boolean estA(Coord coord, Coord autre) {
            return coord.x > autre.x;
        }
    };

    public abstract Coord de(Coord coord, int distance);

    public abstract boolean estA(Coord coord, Coord autre);

    public Coord de(Coord coord) {
        return de(coord, 1);
    }
    public Coord de(Lutin lutin) {
        return de(lutin.coord);
    }
}
