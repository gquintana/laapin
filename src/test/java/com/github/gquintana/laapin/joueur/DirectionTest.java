package com.github.gquintana.laapin.joueur;

import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class DirectionTest  {
    @Test
    public void testCalculer() {
        assertEquals(new Coord(3, 3), Direction.HAUT.calculer(new Coord(3, 2), 1));
        assertEquals(new Coord(3, 1), Direction.BAS.calculer(new Coord(3, 2), 1));
        assertEquals(new Coord(2, 2), Direction.GAUCHE.calculer(new Coord(3, 2), 1));
        assertEquals(new Coord(4, 2), Direction.DROITE.calculer(new Coord(3, 2), 1));
        assertEquals(new Coord(3, 5), Direction.HAUT.calculer(new Coord(3, 2), 3));
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
