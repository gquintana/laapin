package com.github.gquintana.laapin;

import javafx.scene.paint.Color;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

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
        assertThat(configuration.getColor("couleur.hexa"), equalTo(Color.rgb(171, 192, 18)));
        assertThat(configuration.getColor("couleur.nom"), equalTo(Color.GREEN));
        assertThat(configuration.getColor("inconnu"), nullValue());
        assertThat(configuration.getBoolean("booleen", false), is(true));
        assertThat(configuration.getBoolean("inconnu", false), is(false));
    }

    @Test
    public void testLoadGet_Defaut() throws IOException {
        // Given
        Configuration configuration = new Configuration();
        // When
        configuration.load();
        configuration.load(getClass().getResourceAsStream("configuration.properties"));
        // Then
        assertThat(configuration.getString("pardefaut"), is("Defaut"));
        assertThat(configuration.getString("surcharge"), is("Surcharge"));
    }
}