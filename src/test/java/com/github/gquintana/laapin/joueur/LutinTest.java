package com.github.gquintana.laapin.joueur;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class LutinTest {

    @Test
    public void testEstA() throws Exception {
        Carotte carotte = new Carotte(new Coord(4, 2));
        Lapin lapin = new Lapin(new Coord(2, 2), null, null, null, 0, null);
        assertTrue(carotte.estADroite().de(lapin));
    }
}