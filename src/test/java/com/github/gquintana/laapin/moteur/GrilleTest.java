package com.github.gquintana.laapin.moteur;

import com.github.gquintana.laapin.joueur.Coord;
import com.github.gquintana.laapin.joueur.Joueur;
import org.junit.Test;

import static com.github.gquintana.laapin.moteur.Lutins.carotte;
import static com.github.gquintana.laapin.moteur.Lutins.lapin;
import static java.util.Arrays.asList;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class GrilleTest {
    private final Joueur joueurMock = mock(Joueur.class);
    private final Lapin lapin = lapin("Mock", joueurMock, 2,2);
    private final Grille grille = new Grille(new Coord(5,5),
            asList(lapin, lapin("Méchant", 2, 3)),
            asList(carotte(0,0), carotte(3,2)));

    @Test
    public void testContient() {
        assertTrue(grille.contient(new Coord(1,1)));
        assertFalse(grille.contient(new Coord(5,2)));
        assertFalse(grille.contient(new Coord(2,-1)));
    }

    @Test
    public void testGetLapin() {
        assertNull(grille.getLapin(new Coord(1,1)));
        assertNull(grille.getLapin(new Coord(3,2)));
        assertNotNull(grille.getLapin(new Coord(2,3)));
    }

    @Test
    public void testGetCarotte() {
        assertNull(grille.getCarotte(new Coord(1,1)));
        assertNotNull(grille.getCarotte(new Coord(3,2)));
        assertNull(grille.getCarotte(new Coord(2,3)));
    }
}