package com.github.gquintana.laapin.joueur;

public enum TypeAction {
    AVANCER,
    SAUTER,
    FRAPPER,
    SE_REPOSER;
    public Action vers(Direction direction) {
        return new Action(this, direction);
    }
}
