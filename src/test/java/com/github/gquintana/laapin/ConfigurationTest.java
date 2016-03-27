package com.github.gquintana.laapin;

import org.hamcrest.Matchers;
import org.junit.Test;

import java.awt.*;
import java.io.IOException;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.*;

public class ConfigurationTest {
    @Test
    public void testLoadGet() throws IOException {
        // Given
        Configuration configuration = new Configuration();
        // When
        configuration.load(getClass().getResourceAsStream("configuration.properties"));
        // Then
        assertThat(configuration.getInt("entier", 0), is(12));
        assertThat(configuration.getInt("inconnu", 23), is(23));
        assertThat(configuration.getClass("classe"), equalTo(String.class));
        assertThat(configuration.getClass("inconnu"), nullValue());
        assertThat(configuration.getString("chaine"), equalTo("Bonjour le monde"));
        assertThat(configuration.getString("inconnu"), nullValue());
        assertThat(configuration.getColor("couleur.hexa"), equalTo(new Color(171, 192, 18)));
        assertThat(configuration.getColor("couleur.nom"), equalTo(Color.GREEN));
        assertThat(configuration.getColor("inconnu"), nullValue());
    }

}