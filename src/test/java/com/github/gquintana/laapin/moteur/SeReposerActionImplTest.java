package com.github.gquintana.laapin.moteur;

import com.github.gquintana.laapin.joueur.Action;
import com.github.gquintana.laapin.joueur.Coord;
import com.github.gquintana.laapin.joueur.Direction;
import com.github.gquintana.laapin.joueur.TypeAction;
import org.junit.Test;

import static com.github.gquintana.laapin.moteur.Lutins.lapin;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class SeReposerActionImplTest {
    private final SeReposerActionImpl commande = new SeReposerActionImpl();

    @Test
    public void testExecuter_Fatigue() {
        // Given
        Lapin lapin = lapin("Test", 2, 3);
        lapin.fatigue = 2;
        Grille grille = new Grille(new Coord(5, 5), singletonList(lapin), emptyList());
        // When
        commande.executer(lapin, grille, new Action(TypeAction.SE_REPOSER, Direction.BAS));
        // Then
        assertThat(lapin.coord.x, is(2));
        assertThat(lapin.coord.y, is(3));
        assertThat(lapin.fatigue, is(1));

    }
    @Test
    public void testExecuter_EnForme() {
        // Given
        Lapin lapin = lapin("Test", 2, 3);
        Grille grille = new Grille(new Coord(5, 5), singletonList(lapin), emptyList());
        // When
        commande.executer(lapin, grille, new Action(TypeAction.SE_REPOSER, Direction.BAS));
        // Then
        assertThat(lapin.coord.x, is(2));
        assertThat(lapin.coord.y, is(3));
        assertThat(lapin.fatigue, is(0));

    }
}
