package com.github.mixteen.laapin.moteur;

import com.github.mixteen.laapin.joueur.Action;

public class SeReposerActionImpl implements ActionImpl {
    @Override
    public ResultatAction executer(Lapin lapin, Grille grille, Action action) {
        lapin.seReposer();
        return new ResultatAction(lapin, action, "Zzzz");
    }
}
