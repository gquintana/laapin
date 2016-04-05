package com.github.gquintana.laapin.joueur;

import org.junit.Test;

import static com.github.gquintana.laapin.joueur.Lutins.carotte;
import static com.github.gquintana.laapin.joueur.Lutins.lapin;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertTrue;
public class LutinTest {

    @Test
    public void testEstA() throws Exception {
        Carotte carotte = new Carotte(new Coord(4, 2));
        Lapin lapin = new Lapin(new Coord(2, 2), null, null, null, 0, null);
        assertTrue(carotte.estADroite().de(lapin));
    }

    @Test
    public void testEstABuilder() {
        // Given
        Lapin lapin = lapin("Test", 2, 2);
        // When Then
        assertTrue(carotte(4, 2).estADroite().de(lapin));
        assertTrue(carotte(1, 2).estAGauche().de(lapin));
        assertTrue(carotte(2, 4).estEnHaut().de(lapin));
        assertTrue(carotte(2, 1).estEnBas().de(lapin));
    }

    @Test
    public void testDirectionVers() {
        // Given
        Lapin lapin = lapin("Test", 2, 2);
        // When Then
        assertThat(lapin.directionVers(carotte(4, 2)), is(Direction.DROITE));
        assertThat(lapin.directionVers(carotte(1, 2)), is(Direction.GAUCHE));
        assertThat(lapin.directionVers(carotte(2, 4)), is(Direction.HAUT));
        assertThat(lapin.directionVers(carotte(2, 1)), is(Direction.BAS));
    }

    @Test
    public void testCoordVers() {
        // Given
        Lapin lapin = lapin("Test", 2, 2);
        // When Then
        assertThat(lapin.coordVers(Direction.GAUCHE), is(new Coord(1, 2)));
        assertThat(lapin.coordVers(Direction.DROITE), is(new Coord(3, 2)));
        assertThat(lapin.coordVers(Direction.HAUT), is(new Coord(2, 3)));
        assertThat(lapin.coordVers(Direction.BAS), is(new Coord(2, 1)));
    }
}