package com.github.gquintana.laapin.moteur;

import com.github.gquintana.laapin.Configuration;
import org.junit.Test;

import java.util.Random;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class GrilleFactoryTest {

    @Test
    public void testCreerGrille_Normal() throws Exception {
        // Given
        Configuration configuration = new Configuration();
        configuration.load(getClass().getResourceAsStream("laapin-normal.properties"));
        GrilleFactory grilleFactory = new GrilleFactory(configuration, new Random());
        // When
        Grille grille = grilleFactory.creerGrille();
        // Then
        assertThat(grille.carottes.size(), is(5));
        assertThat(grille.lapins.size(), is(2));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreerGrille_Saturee() throws Exception {
        // Given
        Configuration configuration = new Configuration();
        configuration.load(getClass().getResourceAsStream("laapin-saturee.properties"));
        GrilleFactory grilleFactory = new GrilleFactory(configuration, new Random());
        // When
        Grille grille = grilleFactory.creerGrille();
        // Then
    }

}