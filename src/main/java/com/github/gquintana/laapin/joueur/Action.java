package com.github.gquintana.laapin.joueur;

public class Action {
    public final TypeAction type;
    public final Direction direction;
    public Action(TypeAction type, Direction direction) {
        this.type = type;
        this.direction = direction;
    }
}
