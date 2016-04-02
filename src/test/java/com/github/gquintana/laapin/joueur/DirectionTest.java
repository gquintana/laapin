package com.github.gquintana.laapin.joueur;

import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;

public class DirectionTest  {
    @Test
    public void testDe_Distance() {
        assertEquals(new Coord(3, 3), Direction.HAUT.de(new Coord(3, 2), 1));
        assertEquals(new Coord(3, 1), Direction.BAS.de(new Coord(3, 2), 1));
        assertEquals(new Coord(2, 2), Direction.GAUCHE.de(new Coord(3, 2), 1));
        assertEquals(new Coord(4, 2), Direction.DROITE.de(new Coord(3, 2), 1));
        assertEquals(new Coord(3, 5), Direction.HAUT.de(new Coord(3, 2), 3));
    }
    @Test
    public void testEstA() {
        assertTrue(Direction.HAUT.estA(new Coord(2,5), new Coord(2,2)));
        assertTrue(Direction.DROITE.estA(new Coord(3,2), new Coord(2,2)));
        assertTrue(Direction.BAS.estA(new Coord(2,0), new Coord(2,2)));
        assertTrue(Direction.GAUCHE.estA(new Coord(0,2), new Coord(2,2)));
    }

    @Test
    public void testDe() {
        // Given
        Lutin lutin = new Carotte(new Coord(2,2));
        // When
        Coord coord = Direction.DROITE.de(lutin);
        // Then
        assertThat(coord, equalTo(new Coord(3, 2)));
    }
}
