package com.github.gquintana.laapin.joueur;

import com.github.gquintana.laapin.joueur.Coord;
import com.github.gquintana.laapin.joueur.Direction;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

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
}
