package com.github.mixteen.laapin.moteur;

import com.github.mixteen.laapin.joueur.Action;
import com.github.mixteen.laapin.joueur.Coord;
import com.github.mixteen.laapin.joueur.Direction;
import com.github.mixteen.laapin.joueur.TypeAction;
import org.junit.Test;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class AvancerActionImplTest {
    private final AvancerActionImpl commande = new AvancerActionImpl();

    @Test
    public void testExecuter_Normal() throws Exception {
        // Given
        Lapin lapin = Lutins.lapin("Test", 2, 3);
        Grille grille = new Grille(new Coord(5, 5), singletonList(lapin));
        // When
        commande.executer(lapin, grille, new Action(TypeAction.AVANCER, Direction.BAS));
        // Then
        assertThat(lapin.coord.x, is(2));
        assertThat(lapin.coord.y, is(2));
    }

    @Test
    public void testExecuter_Bordure() throws Exception {
        // Given
        Lapin lapin = Lutins.lapin("Test", 4, 3);
        Grille grille = new Grille(new Coord(5, 5), singletonList(lapin));
        // When
        commande.executer(lapin, grille, new Action(TypeAction.AVANCER, Direction.DROITE));
        // Then
        assertThat(lapin.coord.x, is(4));
        assertThat(lapin.coord.y, is(3));
    }

    @Test
    public void testExecuter_AutreLapin() throws Exception {
        // Given
        Lapin lapin = Lutins.lapin("Test", 4, 3);
        Lapin mechant = Lutins.lapin("Méchant", 4, 4);
        Grille grille = new Grille(new Coord(5, 5), asList(lapin, mechant));
        // When
        commande.executer(lapin, grille, new Action(TypeAction.AVANCER, Direction.HAUT));
        // Then
        assertThat(lapin.coord.x, is(4));
        assertThat(lapin.coord.y, is(3));
    }

    @Test
    public void testExecuter_Carotte() throws Exception {
        // Given
        Lapin lapin = Lutins.lapin("Test", 4, 3);
        Carotte carotte = Lutins.carotte(3, 3);
        Grille grille = new Grille(new Coord(5, 5), asList(lapin, carotte));
        // When
        commande.executer(lapin, grille, new Action(TypeAction.AVANCER, Direction.GAUCHE));
        // Then
        assertThat(lapin.coord.x, is(3));
        assertThat(lapin.coord.y, is(3));
    }
    @Test
    public void testExecuter_Rocher() throws Exception {
        // Given
        Lapin lapin = Lutins.lapin("Test", 4, 3);
        Rocher rocher = Lutins.rocher(4, 4);
        Grille grille = new Grille(new Coord(5, 5), asList(lapin, rocher));
        // When
        commande.executer(lapin, grille, new Action(TypeAction.AVANCER, Direction.HAUT));
        // Then
        assertThat(lapin.coord.x, is(4));
        assertThat(lapin.coord.y, is(3));
    }

}