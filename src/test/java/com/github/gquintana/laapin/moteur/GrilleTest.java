package com.github.gquintana.laapin.moteur;

import com.github.gquintana.laapin.joueur.*;
import org.junit.Test;

import static com.github.gquintana.laapin.moteur.Lutins.carotte;
import static com.github.gquintana.laapin.moteur.Lutins.lapin;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

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

    @Test
    public void testReflechir_Manger() {
        // Given
        when(joueurMock.reflechir(any(), any())).thenReturn(new Action(TypeAction.AVANCER, Direction.DROITE));
        // when
        grille.reflechir(lapin);
        // Then
        assertThat(lapin.coord.x, is(3));
        assertThat(lapin.coord.y, is(2));
        assertThat(lapin.score, is(1));
    }

    @Test
    public void testReflechir_Sauter() {
        // Given
        when(joueurMock.reflechir(any(), any())).thenReturn(new Action(TypeAction.SAUTER, Direction.HAUT));
        // when
        grille.reflechir(lapin);
        // Then
        assertThat(lapin.coord.x, is(2));
        assertThat(lapin.coord.y, is(4));
    }

    @Test
    public void testReflechir_Frapper() {
        // Given
        when(joueurMock.reflechir(any(), any())).thenReturn(new Action(TypeAction.FRAPPER, Direction.HAUT));
        // when
        grille.reflechir(lapin);
        // Then
        assertThat(lapin.coord.x, is(2));
        assertThat(lapin.coord.y, is(2));
        assertThat(grille.lapins.get(1).fatigue, is(2));
    }

    @Test
    public void testReflechir_Null() {
        // Given
        when(joueurMock.reflechir(any(), any())).thenReturn(null);
        // when
        grille.reflechir(lapin);
        // Then
        assertThat(lapin.coord.x, is(2));
        assertThat(lapin.coord.y, is(2));
    }

    @Test
    public void testReflechir_Exception() {
        // Given
        when(joueurMock.reflechir(any(), any())).thenThrow(NullPointerException.class);
        // when
        grille.reflechir(lapin);
        // Then
        assertThat(lapin.coord.x, is(2));
        assertThat(lapin.coord.y, is(2));
    }

    @Test
    public void testReflechir_Fatigue() {
        // Given
        lapin.fatigue = 2;
        // when
        grille.reflechir(lapin);
        // Then
        assertThat(lapin.coord.x, is(2));
        assertThat(lapin.coord.y, is(2));
        verifyNoMoreInteractions(joueurMock);
    }
}