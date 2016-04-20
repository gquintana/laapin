package com.github.mixteen.laapin.joueur;

import org.junit.Test;

import java.util.List;

import static org.hamcrest.Matchers.hasItems;
import static org.junit.Assert.*;

public class CoordTest {
    /**
     * 5Y---Y
     * 4 +-X
     * 3 |
     * 2 |
     * 1 X
     * 012345
     */
    @Test
    public void testNombreDePas() {
        assertEquals(1, new Coord(2, 1).distance(new Coord(2, 2)));
        assertEquals(5, new Coord(2, 1).distance(new Coord(4, 4)));
        assertEquals(4, new Coord(5, 5).distance(new Coord(1, 5)));
    }

    @Test
    public void testEquals() {
        assertEquals(new Coord(3, 4), new Coord(3, 4));
        assertNotEquals(new Coord(3, 3), new Coord(3, 4));
        assertNotEquals(new Coord(4, 4), new Coord(3, 4));
        assertNotEquals(new Coord(4, 3), new Coord(3, 4));
    }

    @Test
    public void testEstA() {
        assertTrue(new Coord(4, 2).estA(Direction.DROITE, new Coord(2, 2)));
    }

    @Test
    public void testDirectionVers() {
        // When
        List<Direction> directions = new Coord(2, 2).directionsVers(new Coord(4, 3));
        // Then
        assertThat(directions, hasItems(Direction.HAUT, Direction.DROITE));
    }

    @Test
    public void testEstABuilder() {
        assertTrue(new Coord(4, 2).estADroite().de(new Coord(2, 2)));
        assertTrue(new Coord(1, 2).estAGauche().de(new Coord(2, 2)));
        assertTrue(new Coord(2, 4).estEnHaut().de(new Coord(2, 2)));
        assertTrue(new Coord(2, 1).estEnBas().de(new Coord(2, 2)));
    }

}
