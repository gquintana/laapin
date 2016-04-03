package com.github.gquintana.laapin.moteur;

import org.junit.Test;

import static com.github.gquintana.laapin.moteur.Lutins.lapin;
import static org.junit.Assert.assertEquals;

public class LapinTest {
    @Test
    public void testInitiale() {
        assertEquals("B", lapin("Bob", 0,0).initiale());
        assertEquals("J", lapin(" John ", 0,0).initiale());
        assertEquals("?", lapin(" ", 0,0).initiale());
        assertEquals("?", lapin(null, 0,0).initiale());
    }
}