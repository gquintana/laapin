package com.github.gquintana.laapin.joueur;

import org.junit.Test;

import static com.github.gquintana.laapin.joueur.Action.*;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ActionTest {
    @Test
    public void testActionBuilder() {
        assertThat(frapper().aGauche(), is(new Action(TypeAction.FRAPPER, Direction.GAUCHE)));
        assertThat(sauter().aDroite(), is(new Action(TypeAction.SAUTER, Direction.DROITE)));
        assertThat(avancer().enBas(), is(new Action(TypeAction.AVANCER, Direction.BAS)));
        assertThat(avancer().enHaut(), is(new Action(TypeAction.AVANCER, Direction.HAUT)));
        assertThat(seReposer(), is(new Action(TypeAction.SE_REPOSER, null)));
    }
}