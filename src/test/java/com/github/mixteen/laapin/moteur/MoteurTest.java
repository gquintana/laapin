package com.github.mixteen.laapin.moteur;

import com.github.mixteen.laapin.Configuration;
import com.github.mixteen.laapin.joueur.*;
import org.junit.Test;

import static com.github.mixteen.laapin.moteur.Lutins.carotte;
import static com.github.mixteen.laapin.moteur.Lutins.lapin;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class MoteurTest {
    private final Joueur joueurMock = mock(Joueur.class);
    private final Lapin lapin = lapin("Mock", joueurMock, 2, 2);
    private final com.github.mixteen.laapin.moteur.Grille grille = new com.github.mixteen.laapin.moteur.Grille(new Coord(5, 5),
            asList(lapin, lapin("Méchant", 2, 3),
                    carotte(0, 0), carotte(3, 2)));
    private final Configuration configuration = new Configuration();
    private final Moteur moteur = new Moteur(configuration, new MoteurListener() {
        @Override
        public void onInitialiser(com.github.mixteen.laapin.moteur.Grille grille) {

        }

        @Override
        public void onAgir(com.github.mixteen.laapin.moteur.Grille grille, Lapin lapin, ResultatAction resultatAction) {

        }

        @Override
        public void onArreter(com.github.mixteen.laapin.moteur.Grille grille) {

        }
    }, grille);

    @Test
    public void testReflechir_Manger() {
        // Given
        when(joueurMock.reflechir(any(), any())).thenReturn(new Action(TypeAction.AVANCER, Direction.DROITE));
        // when
        moteur.reflechir(lapin);
        // Then
        assertThat(lapin.coord.x, is(3));
        assertThat(lapin.coord.y, is(2));
        assertThat(lapin.score(), is(1));
    }

    @Test
    public void testReflechir_Sauter() {
        // Given
        when(joueurMock.reflechir(any(), any())).thenReturn(new Action(TypeAction.SAUTER, Direction.HAUT));
        // when
        moteur.reflechir(lapin);
        // Then
        assertThat(lapin.coord.x, is(2));
        assertThat(lapin.coord.y, is(4));
    }

    @Test
    public void testReflechir_Frapper() {
        // Given
        when(joueurMock.reflechir(any(), any())).thenReturn(new Action(TypeAction.FRAPPER, Direction.HAUT));
        // when
        moteur.reflechir(lapin);
        // Then
        assertThat(lapin.coord.x, is(2));
        assertThat(lapin.coord.y, is(2));
        assertThat(grille.lapins().get(1).energie(), is(-2));
    }

    @Test
    public void testReflechir_Null() {
        // Given
        when(joueurMock.reflechir(any(), any())).thenReturn(null);
        // when
        moteur.reflechir(lapin);
        // Then
        assertThat(lapin.coord.x, is(2));
        assertThat(lapin.coord.y, is(2));
    }

    @Test
    public void testReflechir_Exception() {
        // Given
        when(joueurMock.reflechir(any(), any())).thenThrow(NullPointerException.class);
        // when
        moteur.reflechir(lapin);
        // Then
        assertThat(lapin.coord.x, is(2));
        assertThat(lapin.coord.y, is(2));
    }

    @Test
    public void testReflechir_Fatigue() {
        // Given
        lapin.recevoirCoup();
        // when
        moteur.reflechir(lapin);
        // Then
        assertThat(lapin.coord.x, is(2));
        assertThat(lapin.coord.y, is(2));
        verifyNoMoreInteractions(joueurMock);
    }
}