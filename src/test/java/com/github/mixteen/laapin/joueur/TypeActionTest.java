package com.github.mixteen.laapin.joueur;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class TypeActionTest {

    @Test
    public void testVers() throws Exception {
        // Given

        // When
        Action action = TypeAction.AVANCER.vers(Direction.GAUCHE);
        // Then
        assertThat(action.direction, is(Direction.GAUCHE));
        assertThat(action.type, is(TypeAction.AVANCER));

    }
}