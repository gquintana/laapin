package com.github.mixteen.laapin.moteur;

import com.github.mixteen.laapin.joueur.Coord;
import com.github.mixteen.laapin.joueur.Joueur;
import org.junit.Test;

import static java.util.Arrays.asList;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class GrilleTest {
    private final Joueur joueurMock = mock(Joueur.class);
    private final Lapin lapin = Lutins.lapin("Mock", joueurMock, 2, 2);
    private final Grille grille = new Grille(new Coord(5, 5),
            asList(lapin, Lutins.lapin("Méchant", 2, 3),
                    Lutins.carotte(0, 0), Lutins.carotte(3, 2)));

    @Test
    public void testContientObstacle() {
        assertFalse(grille.contientObstacle(new Coord(1, 1)));
        assertTrue(grille.contientObstacle(new Coord(5, 2)));
        assertTrue(grille.contientObstacle(new Coord(2, -1)));
        assertTrue(grille.contientObstacle(new Coord(2, 3)));
    }

    @Test
    public void testGetLapin() {
        assertNull(grille.getLapin(new Coord(1, 1)));
        assertNull(grille.getLapin(new Coord(3, 2)));
        assertNotNull(grille.getLapin(new Coord(2, 3)));
    }

    @Test
    public void testGetCarotte() {
        assertNull(grille.getCarotte(new Coord(1, 1)));
        assertNotNull(grille.getCarotte(new Coord(3, 2)));
        assertNull(grille.getCarotte(new Coord(2, 3)));
    }
}