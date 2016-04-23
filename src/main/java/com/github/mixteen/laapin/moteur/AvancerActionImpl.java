package com.github.mixteen.laapin.moteur;

import com.github.mixteen.laapin.joueur.Action;
import com.github.mixteen.laapin.joueur.Coord;

public class AvancerActionImpl implements ActionImpl {
    @Override
    public ResultatAction executer(Lapin lapin, Grille grille, Action action) {
        Coord coordCible = action.direction.de(lapin.coord);
        if (grille.contientObstacle(coordCible)) {
            return new ResultatAction(lapin, action, "Pas par là");
        }
        lapin.setCoord(coordCible);
        ResultatAction resultatAction = manger(lapin, grille, action, coordCible);
        if (resultatAction != null) return resultatAction;
        return new ResultatAction(lapin, action, "En avant");
    }

    static ResultatAction manger(Lapin lapin, Grille grille, Action action, Coord coordCible) {
        Carotte carotte = grille.getCarotte(coordCible);
        if (carotte != null) {
            ResultatAction resultatAction = new ResultatAction(lapin, action, "Miam");
            lapin.manger(carotte);
            grille.supprimerCarotte(carotte);
            return resultatAction;
        }
        return null;
    }
}
