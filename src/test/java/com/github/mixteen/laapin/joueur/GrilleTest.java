package com.github.mixteen.laapin.joueur;

import org.junit.Test;

import java.util.stream.Collectors;

import static com.github.mixteen.laapin.joueur.Lutins.*;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class GrilleTest {
    private final Lapin moi = lapin("Moi", 2, 2);
    /**
     * 4L   R
     * 3  C
     * 2  L
     * 1  L
     * 0    C
     * +01234
     */
    private final Grille grille = new Grille(new Coord(5, 5),
            asList(moi, lapin("Moche", 2, 1), lapin("Méchant", 0, 4),
                    carotte(4, 0), carotte(2, 3),
                    rocher(4,4)));

    @Test
    public void testCarotte() {
        assertThat(grille.carotteProche(moi).coord, equalTo(new Coord(2, 3)));
        assertThat(grille.carottesProches(moi).stream().map(l -> l.coord).collect(Collectors.toList()), hasItems(new Coord(2, 3), new Coord(4, 0)));
        assertThat(grille.carottesProches(moi).stream().map(l -> l.coord).collect(Collectors.toList()), hasItems(new Coord(2, 3), new Coord(4, 0)));
        assertThat(grille.contientCarotte(new Coord(2, 3)), is(true));
        assertThat(grille.contientCarotte(new Coord(3, 2)), is(false));
        assertThat(grille.lutin(new Coord(2, 3)), instanceOf(Carotte.class));
    }

    @Test
    public void testGetLapin() {
        assertThat(grille.lapinProche(moi).coord, equalTo(new Coord(2, 1)));
        assertThat(grille.lapinsProches(moi).stream().map(l -> l.coord).collect(Collectors.toList()), hasItems(new Coord(2, 1), new Coord(0, 4)));
        assertThat(grille.contientLapin(new Coord(2, 1)), is(true));
        assertThat(grille.contientLapin(new Coord(1, 2)), is(false));
        assertThat(grille.lutin(new Coord(2, 1)), instanceOf(Lapin.class));
    }
    @Test
    public void testRocher() {
        assertThat(grille.contientRocher(new Coord(4, 4)), is(true));
        assertThat(grille.contientRocher(new Coord(1, 2)), is(false));
        assertThat(grille.lutin(new Coord(4, 4)), instanceOf(Rocher.class));
    }

    @Test
    public void testVide() {
        assertThat(grille.lutin(new Coord(3, 2)), nullValue());

    }
}
