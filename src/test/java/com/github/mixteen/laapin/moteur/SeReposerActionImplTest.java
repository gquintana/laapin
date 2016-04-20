package com.github.mixteen.laapin.moteur;

import com.github.mixteen.laapin.joueur.Action;
import com.github.mixteen.laapin.joueur.Coord;
import com.github.mixteen.laapin.joueur.Direction;
import com.github.mixteen.laapin.joueur.TypeAction;
import org.junit.Test;

import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class SeReposerActionImplTest {
    private final SeReposerActionImpl commande = new SeReposerActionImpl();

    @Test
    public void testExecuter_Fatigue() {
        // Given
        Lapin lapin = Lutins.lapin("Test", 2, 3);
        lapin.recevoirCoup();
        Grille grille = new Grille(new Coord(5, 5), singletonList(lapin));
        // When
        commande.executer(lapin, grille, new Action(TypeAction.SE_REPOSER, Direction.BAS));
        // Then
        assertThat(lapin.coord.x, is(2));
        assertThat(lapin.coord.y, is(3));
        assertThat(lapin.energie(), is(-1));

    }
    @Test
    public void testExecuter_EnForme() {
        // Given
        Lapin lapin = Lutins.lapin("Test", 2, 3);
        Grille grille = new Grille(new Coord(5, 5), singletonList(lapin));
        // When
        commande.executer(lapin, grille, new Action(TypeAction.SE_REPOSER, Direction.BAS));
        // Then
        assertThat(lapin.coord.x, is(2));
        assertThat(lapin.coord.y, is(3));
        assertThat(lapin.energie(), is(0));

    }
}
