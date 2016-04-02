package com.github.gquintana.laapin.joueur;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ScriptJoueurTest {

    @Test
    public void testReflechir_SeReposer() throws Exception {
        // Given
        ScriptJoueur joueur = new ScriptJoueur("classpath:joueur/TestJoueur.js");
        // When
        Action action = joueur.reflechir(Lutins.lapin("SeRepose", 1, 2), null);
        // Then
        assertThat(action.type, is(TypeAction.SE_REPOSER));

    }

    @Test
    public void testReflechir_Avancer() throws Exception {
        // Given
        ScriptJoueur joueur = new ScriptJoueur("classpath:joueur/TestJoueur.js");
        // When
        Action action = joueur.reflechir(Lutins.lapin("Avance", 2, 2), null);
        // Then
        assertThat(action.type, is(TypeAction.AVANCER));

    }


    @Test
    public void testReflechir_Frapper() throws Exception {
        // Given
        ScriptJoueur joueur = new ScriptJoueur("classpath:joueur/TestJoueur.js");
        // When
        Action action = joueur.reflechir(Lutins.lapin("Frappe", 3, 2), null);
        // Then
        assertThat(action.type, is(TypeAction.FRAPPER));

    }


    @Test
    public void testReflechir_Sauter() throws Exception {
        // Given
        ScriptJoueur joueur = new ScriptJoueur("classpath:joueur/TestJoueur.js");
        // When
        Action action = joueur.reflechir(Lutins.lapin("Saute", 4, 2), null);
        // Then
        assertThat(action.type, is(TypeAction.SAUTER));

    }

}