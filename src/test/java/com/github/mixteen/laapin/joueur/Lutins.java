package com.github.mixteen.laapin.joueur;

public class Lutins {
    public static Lapin lapin(String nom, int x, int y) {
        return new Lapin(new Coord(x, y), nom, null, null, 0, null);
    }
    public static Carotte carotte(int x, int y) {
        return new Carotte(new Coord(x, y));
    }
    public static Rocher rocher(int x, int y) {
        return new Rocher(new Coord(x, y));
    }
}
