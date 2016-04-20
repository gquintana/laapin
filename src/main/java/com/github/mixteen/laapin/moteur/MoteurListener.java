package com.github.mixteen.laapin.moteur;

public interface MoteurListener {
    void onInitialiser(Grille grille);
    void onAgir(Grille grille, Lapin lapin, ResultatAction resultatAction);
    void onArreter(Grille grille);
}
