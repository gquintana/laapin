package com.github.gquintana.laapin.joueur;

import com.github.gquintana.laapin.joueur.*;
import com.github.gquintana.laapin.joueur.Carotte;
import com.github.gquintana.laapin.joueur.Grille;
import com.github.gquintana.laapin.joueur.Lapin;

import java.awt.*;
import java.awt.List;
import java.util.*;

public class DummyJoueur implements Joueur {
    @Override
    public Action reflechir(Lapin monLapin, Grille grille) {
        return new Action(TypeAction.SE_REPOSER, null);
    }
}
