package com.github.gquintana.laapin.joueur;

import org.junit.Test;

import static com.github.gquintana.laapin.joueur.Lutins.*;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class DistancierTest {

    /**
     * 4    C
     * 3 RRRR
     * 2 R  L
     * 1 RR
     * 0
     * X01234
     */
    @Test
    public void testDistance() throws Exception {
        // Given
        Lapin lapin = lapin("Lapin", 4, 2);
        Carotte carotte = carotte(4, 4);
        Grille grille = new Grille(new Coord(5, 5), asList(
                lapin, carotte,
                rocher(4, 3), rocher(3, 3), rocher(2, 3), rocher(1, 3), rocher(1, 2), rocher(1, 1), rocher(2, 1)
        ));
        // When
        Distancier distancier = new Distancier(grille, carotte.coord);
        // Then
        int[][] distances = new int[][]{
                {4, 3, 2, 1, 0},
                {5, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE},
                {6, Integer.MAX_VALUE, 14, 13, Integer.MAX_VALUE},
                {7, Integer.MAX_VALUE, Integer.MAX_VALUE, 12, 13},
                {8, 9, 10, 11, 12}
        };
        for (int y = 0; y < 5; y++) {
            for (int x = 0; x < 5; x++) {
                Coord c = new Coord(x, y);
                assertEquals("Distance " + c, distances[grille.taille.y-y-1][x], distancier.distance(c));
            }
        }
    }
    /**
     * 4    C
     * 3 RRRR
     * 2 R  L
     * 1 RR
     * 0
     * X01234
     */
    @Test
    public void testDirectionDepuis() throws Exception {
        // Given
        Lapin lapin = lapin("Lapin", 4, 2);
        Carotte carotte = carotte(4, 4);
        Grille grille = new Grille(new Coord(5, 5), asList(
                lapin, carotte,
                rocher(4, 3), rocher(3, 3), rocher(2, 3), rocher(1, 3), rocher(1, 2), rocher(1, 1), rocher(2, 1)
        ));
        // When
        Distancier distancier = new Distancier(grille, carotte.coord);
        // Then
        assertThat(distancier.directionDepuis(lapin.coord), is(Direction.BAS));
        assertThat(distancier.directionDepuis(new Coord(2,2)), is(Direction.DROITE));
        assertThat(distancier.directionDepuis(new Coord(4,0)), is(Direction.GAUCHE));
        assertThat(distancier.directionDepuis(new Coord(0,0)), is(Direction.HAUT));
    }
}