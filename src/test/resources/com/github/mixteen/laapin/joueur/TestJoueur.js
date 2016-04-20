switch(monLapin.coord.x) {
    case 1:
        joueur.seReposer();
        break;
    case 2:
        joueur.avancer().aDroite();
        break;
    case 3:
        joueur.frapper().enHaut();
        break;
    case 4:
        joueur.sauter().enBas();
}
