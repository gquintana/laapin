var TypeAction = Java.type("com.github.mixteen.laapin.joueur.TypeAction");
var Direction = Java.type("com.github.mixteen.laapin.joueur.Direction");
var carotte = grille.carotteProche(monLapin);
if (carotte) {
    if (carotte.estADroite().de(monLapin)) {
        joueur.avancer().aDroite();
    }
}
