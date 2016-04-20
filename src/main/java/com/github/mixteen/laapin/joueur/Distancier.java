package com.github.mixteen.laapin.joueur;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Calcule les distance dans un grille à partir d'un point d'origine, en tenant compte des obstacles
 */
public class Distancier {
    private final Grille grille;
    private final Coord origine;
    private final int[][] distances;

    public Distancier(Grille grille, Coord origine) {
        this.grille = grille;
        this.origine = origine;
        distances = new int[grille.taille.y][grille.taille.x];
        calculer();
    }


    private List<Coord> coordVoisines(Coord coord) {
        return Arrays.stream(Direction.values())
                .map(d -> d.de(coord))
                .filter(grille::estAccessible)
                .collect(toList());
    }

    /**
     * Donne la distance pour aller vers l'origine
     */
    public int distance(Coord coord) {
        return distances[coord.y][coord.x];
    }

    /**
     * Remplit la matrice de distances
     */
    private void calculer() {
        // Initialise les distances
        for (int y = 0; y < grille.taille.y; y++) {
            for (int x = 0; x < grille.taille.x; x++) {
                distances[y][x] = Integer.MAX_VALUE;
            }
        }
        distances[origine.y][origine.x] = 0;
        LinkedList<Coord> aCalculer = new LinkedList<>();
        aCalculer.addAll(coordVoisines(origine));
        while (!aCalculer.isEmpty()) {
            Coord c = aCalculer.removeFirst();
            int d = Integer.MAX_VALUE;
            for (Coord v : coordVoisines(c)) {
                if (distance(v) == Integer.MAX_VALUE) { // A calculer
                    if (!aCalculer.contains(v)) {
                        aCalculer.add(v);
                    }
                } else {
                    d = Math.min(d, distance(v)); // Déjà calculée
                }
            }
            distances[c.y][c.x] = d + 1;
        }
    }

    /**
     * Donne la direction du chemin le plus court pour aller vers l'origine
     */
    public Direction directionDepuis(Coord coord) {
        Direction directionResult = null;
        int distanceResult = Integer.MAX_VALUE;
        for (Direction direction : Direction.values()) {
            Coord voisin = direction.de(coord);
            if (grille.estAccessible(voisin)) {
                if (directionResult == null || distance(voisin) < distanceResult) {
                    directionResult = direction;
                    distanceResult = distance(voisin);
                }
            }
        }
        return directionResult;
    }

    public Direction directionDepuis(Lutin lutin) {
        return directionDepuis(lutin.coord);
    }

    public int distance(Lutin lutin) {
        return distance(lutin.coord);
    }

    public int distanceMax() {
        return Arrays.stream(distances).flatMapToInt(Arrays::stream).filter(d -> d <= (grille.taille.x + grille.taille.y)).max().orElse(0);
    }
}
