var TypeAction = Java.type("com.github.mixteen.laapin.joueur.TypeAction");
var Direction = Java.type("com.github.mixteen.laapin.joueur.Direction");
var carotte = grille.carotteProche(monLapin);
if (carotte) {
    if (carotte.estADroite().de(monLapin)) {
        joueur.avancer().aDroite();
    } else if (carotte.estAGauche().de(monLapin)) {
        joueur.avancer().aGauche();
    } else if (carotte.estEnHaut().de(monLapin)) {
        joueur.avancer().enHaut();
    } else if (carotte.estEnBas().de(monLapin)) {
        joueur.avancer().enBas();
    }
}
