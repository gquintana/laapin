package com.github.mixteen.laapin.joueur;

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

    /**
     * Calcule les coordonnées, depuis la case donnée, la direction donnée et la distance donnée
     */
    public abstract Coord de(Coord coord, int distance);

    /**
     * Calcule les coordonnées, depuis le lutin donné, la direction donnée et la distance donnée
     */
    public Coord de(Lutin lutin, int distance) {
        return de(lutin.coord, distance);
    }

    /**
     * Test si la case <code>coord</code> et dans la direction donnée par rapport à <code>autre</code>
     */
    public abstract boolean estA(Coord coord, Coord autre);

    /**
     * Calcule les coordonnées, depuis la case donnée et la direction donnée
     */
    public Coord de(Coord coord) {
        return de(coord, 1);
    }

    /**
     * Calcule les coordonnées, depuis le lutin donné et la direction donnée
     */
    public Coord de(Lutin lutin) {
        return de(lutin.coord);
    }

}
