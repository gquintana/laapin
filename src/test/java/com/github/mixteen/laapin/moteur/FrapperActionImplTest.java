package com.github.mixteen.laapin.moteur;

import com.github.mixteen.laapin.joueur.Action;
import com.github.mixteen.laapin.joueur.Coord;
import com.github.mixteen.laapin.joueur.Direction;
import com.github.mixteen.laapin.joueur.TypeAction;
import org.junit.Test;

import static com.github.mixteen.laapin.moteur.Lutins.lapin;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertThat;

public class FrapperActionImplTest {
    private final FrapperActionImpl commande = new FrapperActionImpl();

    @Test
    public void testExecuter_Raté() throws Exception {
        // Given
        Lapin lapin = lapin("Test", 2, 3);
        Grille grille = new Grille(new Coord(5, 5), singletonList(lapin));
        // When
        commande.executer(lapin, grille, new Action(TypeAction.FRAPPER, Direction.BAS));
        // Then
        assertThat(lapin.coord.x, is(2));
        assertThat(lapin.coord.y, is(3));
    }

    @Test
    public void testExecuter_Bordure() throws Exception {
        // Given
        Lapin lapin = lapin("Test", 4, 3);
        Grille grille = new Grille(new Coord(5, 5), singletonList(lapin));
        // When
        commande.executer(lapin, grille, new Action(TypeAction.FRAPPER, Direction.DROITE));
        // Then
        assertThat(lapin.coord.x, is(4));
        assertThat(lapin.coord.y, is(3));
    }

    @Test
    public void testExecuter_AutreLapin() throws Exception {
        // Given
        Lapin lapin = lapin("Test", 4, 3);
        Lapin mechant = lapin("Méchant", 4, 4);
        Grille grille = new Grille(new Coord(5, 5), asList(lapin, mechant));
        // When
        commande.executer(lapin, grille, new Action(TypeAction.FRAPPER, Direction.HAUT));
        // Then
        assertThat(lapin.coord.x, is(4));
        assertThat(lapin.coord.y, is(3));
        assertThat(mechant.energie(),  lessThan(0));
    }

    @Test
    public void testExecuter_Carotte() throws Exception {
        // Given
        Lapin lapin = lapin("Test", 4, 3);
        Carotte carotte = new Carotte(new Coord(3, 3));
        Grille grille = new Grille(new Coord(5, 5), asList(lapin, carotte));
        // When
        commande.executer(lapin, grille, new Action(TypeAction.FRAPPER, Direction.GAUCHE));
        // Then
        assertThat(lapin.coord.x, is(4));
        assertThat(lapin.coord.y, is(3));
    }
}