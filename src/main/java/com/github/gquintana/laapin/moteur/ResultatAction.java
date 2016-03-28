package com.github.gquintana.laapin.moteur;

import com.github.gquintana.laapin.joueur.Action;

public class ResultatAction {
    public final Action action;
    public final String message;

    public ResultatAction(Action action, String message) {
        this.action = action;
        this.message = message;
    }
}
