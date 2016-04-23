package com.github.mixteen.laapin.moteur;

import com.github.mixteen.laapin.joueur.Action;
import com.github.mixteen.laapin.joueur.Coord;

public class SauterActionImpl implements ActionImpl {
    @Override
    public ResultatAction executer(Lapin lapin, Grille grille, Action action) {
        Coord coordCible = action.direction.de(lapin.coord);
        Coord coordCible2 = action.direction.de(lapin.coord, 2);
        Lapin lapinCible = grille.getLapin(coordCible);
        if (lapinCible == null) {
            return new ResultatAction(lapin, action, "???");
        }
        if (grille.contientObstacle(coordCible2)) {
            return new ResultatAction(lapin, action, "Pas par là");
        }
        lapin.setCoord(coordCible2);
        ResultatAction resultatAction = AvancerActionImpl.manger(lapin, grille, action, coordCible2);
        if (resultatAction!= null) return resultatAction;
        return new ResultatAction(lapin, action, "Et hop");
    }
}
