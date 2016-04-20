package com.github.mixteen.laapin.joueur;

import com.github.mixteen.laapin.Main;

import java.io.IOException;

import static com.github.mixteen.laapin.joueur.Action.avancer;
import static com.github.mixteen.laapin.joueur.Action.seReposer;

public class Exercice1Joueur implements Joueur {
    @Override
    public Action reflechir(Lapin monLapin, Grille grille) {
        Carotte carotte = grille.carotteProche(monLapin);
        if (carotte == null) {
            return seReposer();
        } else if (carotte.estADroite().de(monLapin)) {
            return avancer().aDroite();
        }
        return null;
    }

    public static void main(String[] args) throws IOException {
        Main.main(new String[]{"classpath:/laapin_exercice_1.properties"});
    }

}
