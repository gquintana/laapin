package com.github.gquintana.laapin.moteur;

import com.github.gquintana.laapin.joueur.Action;

public class ResultatAction {
    public final Lapin lapin;
    public final Action action;
    public final String message;

    public ResultatAction(Lapin lapin, Action action, String message) {
        this.lapin = lapin;
        this.action = action;
        this.message = message;
    }
}
