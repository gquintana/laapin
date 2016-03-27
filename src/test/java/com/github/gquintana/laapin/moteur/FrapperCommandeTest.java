package com.github.gquintana.laapin.moteur;

import com.github.gquintana.laapin.joueur.*;
import org.hamcrest.CoreMatchers;
import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;
import static com.github.gquintana.laapin.moteur.Lutins.*;

public class FrapperCommandeTest {
    private final FrapperCommande commande = new FrapperCommande();

    @Test
    public void testExecuter_Raté() throws Exception {
        // Given
        Lapin lapin = lapin("Test", 2, 3);
        Grille grille = new Grille(new Coord(5, 5), singletonList(lapin), emptyList());
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
        Grille grille = new Grille(new Coord(5, 5), singletonList(lapin), emptyList());
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
        Grille grille = new Grille(new Coord(5, 5), asList(lapin, mechant), emptyList());
        // When
        commande.executer(lapin, grille, new Action(TypeAction.FRAPPER, Direction.HAUT));
        // Then
        assertThat(lapin.coord.x, is(4));
        assertThat(lapin.coord.y, is(3));
        assertThat(mechant.fatigue,  greaterThan(0));
    }

    @Test
    public void testExecuter_Carotte() throws Exception {
        // Given
        Lapin lapin = lapin("Test", 4, 3);
        Carotte carotte = new Carotte(new Coord(3, 3));
        Grille grille = new Grille(new Coord(5, 5), singletonList(lapin), singletonList(carotte));
        // When
        commande.executer(lapin, grille, new Action(TypeAction.FRAPPER, Direction.GAUCHE));
        // Then
        assertThat(lapin.coord.x, is(4));
        assertThat(lapin.coord.y, is(3));
    }
}