var TypeAction = Java.type("com.github.gquintana.laapin.joueur.TypeAction");
var Direction = Java.type("com.github.gquintana.laapin.joueur.Direction");
var carotte = grille.carotteProche(monLapin);
if (carotte) {
    if (carotte.estA(Direction.DROITE).de(monLapin)) {
        joueur.avancer().aDroite();
    } else if (carotte.estA(Direction.GAUCHE).de(monLapin)) {
        joueur.avancer().aGauche();
    } else if (carotte.estA(Direction.HAUT).de(monLapin)) {
        joueur.avancer().enHaut();
    } else if (carotte.estA(Direction.BAS).de(monLapin)) {
        joueur.avancer().enBas();
    }
}
