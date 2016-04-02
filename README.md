# Laapin

Laapin est un jeu multi-joueur dans lequel les participants doivent implémenter la logique,
l'intelligence pour ramasser le plus de carottes possible.

## Configuration

```properties
# Graine aléatoire
random=10

# Taille du plateau de jeu
grille.taille.x=10
grille.taille.y=10

# Nombre de carottes
carottes=10

# Nombre de lapins
lapins=2
# Implémentation du joueur 1
lapin.0.class=com.github.gquintana.laapin.joueur.Bob
# Nom du joueur 1
lapin.0.nom=Bob
# Couleur du joueur 1
lapin.0.couleur=RED

# Implémentation du joueur 2
lapin.1.class=com.github.gquintana.laapin.joueur.Bill
# Nom du joueur 2
lapin.1.nom=Bill
# Couleur du joueur 2
lapin.1.couleur=BLUE
```

## Implémentation

### En Java

Implémenter l'interface `Joueur`
```java
public class DemoJoueur implements Joueur {
    @Override
    public Action reflechir(Lapin monLapin, Grille grille) {
        // Cherche la carotte la plus proche
        Carotte carotte = grille.carotteProche(monLapin);
        if (carotte != null) {
            // Si la carotte la plus proche est à droite, alors on va à droite
            if (carotte.estA(Direction.DROITE).de(monLapin)) {
                return TypeAction.AVANCER.vers(Direction.DROITE);
            }
        }
    }
}
```

### En JavaScript

```javascript
var carotte = grille.carotteProche(monLapin);
if (carotte) {
    if (carotte.estA(Direction.DROITE).de(monLapin)) {
        joueur.avancer().aDroite();
```