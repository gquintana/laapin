package com.github.gquintana.laapin.joueur;

import java.util.List;

/**
 * Un lutin est truc sur la grille: soit un lapin, une carotte, rocher...
 */
public class Lutin {

    public final Coord coord;

    protected Lutin(Coord coord) {
        this.coord = coord;
    }


    /**
     * Teste si le lutin est en haut/bas/gauche/droite d'un autre lutin
     */
    public DirectionPredicateBuilder estA(Direction direction) {
        return new DirectionPredicateBuilder(this, direction);
    }

    /**
     * Teste si le lutin est à droite d'un autre lutin
     */
    public DirectionPredicateBuilder estADroite() {
        return estA(Direction.DROITE);
    }

    /**
     * Teste si le lutin est à gauche d'un autre lutin
     */
    public DirectionPredicateBuilder estAGauche() {
        return estA(Direction.GAUCHE);
    }

    /**
     * Teste si le lutin est en haut d'un autre lutin
     */
    public DirectionPredicateBuilder estEnHaut() {
        return estA(Direction.HAUT);
    }

    /**
     * Teste si le lutin est en bas d'un autre lutin
     */
    public DirectionPredicateBuilder estEnBas() {
        return estA(Direction.BAS);
    }

    /**
     * Calcule la distance à vol d'oiseau pour aller jusqu'à un autre lutin
     */
    public int distance(Lutin lutin) {
        return coord.distance(lutin.coord);
    }

    /**
     * Calcule une direction pour aller vers un autre lutin
     */
    public Direction directionVers(Lutin lutin) {
        return coord.directionsVers(lutin.coord).get(0);

    }
    /**
     * Calcule la ou les directions pour aller vers un autre lutin
     */
    public List<Direction> directionsVers(Lutin lutin) {
        return coord.directionsVers(lutin.coord);

    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " " + coord;
    }

    /**
     * Calcule les coordonnées depuis ce lutin, dans une direction donnée et un nombre de cases
     */
    public Coord coordVers(Direction direction, int distance) {
        return direction.de(this, distance);
    }

    /**
     * Calcule les coordonnées depuis ce lutin, dans une direction donnée
     */
    public Coord coordVers(Direction direction) {
        return direction.de(this);
    }
}
