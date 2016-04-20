package com.github.mixteen.laapin.moteur;

import com.github.mixteen.laapin.joueur.Action;

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
